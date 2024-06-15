package org.figuramc.figura.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.ducks.GameRendererAccessor;
import org.figuramc.figura.lua.api.ClientAPI;
import org.figuramc.figura.math.matrix.FiguraMat4;
import org.figuramc.figura.math.vector.FiguraVec3;
import org.figuramc.figura.utils.ColorUtils;
import org.figuramc.figura.utils.EntityUtils;
import org.figuramc.figura.utils.RenderUtils;
import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements GameRendererAccessor {

    @Shadow @Final Minecraft minecraft;
    @Shadow PostChain postEffect;
    @Shadow private boolean effectActive;
    @Shadow private float fov;

    @Unique
    Operation<GameRenderer> figura$bobViewOP;

    @Unique
    Operation<GameRenderer> figura$bobHurtOP;
    @Shadow protected abstract double getFov(Camera camera, float tickDelta, boolean changingFov);
    @Shadow protected abstract void loadEffect(ResourceLocation id);
    @Shadow public abstract void checkEntityPostEffect(Entity entity);

    @Shadow protected abstract void bobHurt(PoseStack poseStack, float f);

    @Shadow protected abstract void bobView(PoseStack poseStack, float f);

    @Shadow public abstract Minecraft getMinecraft();

    @Shadow @Final private Camera mainCamera;
    @Unique
    private boolean avatarPostShader = false;
    @Unique
    private boolean hasShaders;

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;rotation(Lorg/joml/Quaternionfc;)Lorg/joml/Matrix4f;") )
    private Matrix4f onCameraRotation(Matrix4f instance, Quaternionfc quat, Operation<Matrix4f> original) {
        Avatar avatar = AvatarManager.getAvatar(this.minecraft.getCameraEntity() == null ? this.minecraft.player : this.minecraft.getCameraEntity());
        if (!RenderUtils.vanillaModelAndScript(avatar))
            return original.call(instance, quat);

        // part of the bobbing fix
        if (!hasShaders) {
            PoseStack stack = new PoseStack();
            stack.last().pose().set(instance);

            this.bobHurt(stack, this.mainCamera.getPartialTickTime());
            if (this.minecraft.options.bobView().get()) {
                this.bobView(stack, this.mainCamera.getPartialTickTime());
            }

            instance.set(stack.last().pose());
        }

        float z = 0f;

        FiguraVec3 rot = avatar.luaRuntime.renderer.cameraRot;
        if (rot != null)
            z = (float) rot.z;

        FiguraVec3 offset = avatar.luaRuntime.renderer.cameraOffsetRot;
        if (offset != null)
            z += (float) offset.z;

        instance.rotationZ(z);

        FiguraMat4 mat = avatar.luaRuntime.renderer.cameraMat;
        if (mat != null)
            instance.set(mat.toMatrix4f());

       // FiguraMat3 normal = avatar.luaRuntime.renderer.cameraNormal;
      //  if (normal != null)
      //      stack.last().normal().set(normal.toMatrix3f());
        return original.call(instance, quat);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void render(DeltaTracker deltaTracker, boolean tick, CallbackInfo ci) {
        Entity entity = this.minecraft.getCameraEntity();
        Avatar avatar = AvatarManager.getAvatar(entity);
        if (!RenderUtils.vanillaModelAndScript(avatar)) {
            if (avatarPostShader) {
                avatarPostShader = false;
                this.checkEntityPostEffect(entity);
            }
            return;
        }

        ResourceLocation resource = avatar.luaRuntime.renderer.postShader;
        if (resource == null) {
            if (avatarPostShader) {
                avatarPostShader = false;
                this.checkEntityPostEffect(entity);
            }
            return;
        }

        try {
            avatarPostShader = true;
            this.effectActive = true;
            if (this.postEffect == null || !this.postEffect.getName().equals(resource.toString()))
                if (this.getMinecraft().getResourceManager().getResource(resource).isPresent()) {
                    this.loadEffect(resource);
                } else {
                    FiguraMod.sendChatMessage(Component.literal("Could not load %s as it was not a valid or present post effect.".formatted(resource.toString())).setStyle(ColorUtils.Colors.RED.style));
                }
        } catch (Exception ignored) {
            this.effectActive = false;
            avatar.luaRuntime.renderer.postShader = null;
        }
    }

    @Inject(method = "checkEntityPostEffect", at = @At("HEAD"), cancellable = true)
    private void checkEntityPostEffect(Entity entity, CallbackInfo ci) {
        if (avatarPostShader)
            ci.cancel();
    }

    @Inject(method = "tickFov", at = @At("RETURN"))
    private void tickFov(CallbackInfo ci) {
        Avatar avatar = AvatarManager.getAvatar(this.minecraft.getCameraEntity());
        if (RenderUtils.vanillaModelAndScript(avatar)) {
            Float fov = avatar.luaRuntime.renderer.fov;
            if (fov != null) this.fov = fov;
        }
    }

    @Inject(method = "pick(F)V", at = @At("RETURN"))
    private void pick(float tickDelta, CallbackInfo ci) {
        FiguraMod.pushProfiler(FiguraMod.MOD_ID);
        FiguraMod.pushProfiler("extendedPick");
        FiguraMod.extendedPickEntity = EntityUtils.getViewedEntity(32);
        FiguraMod.popProfiler(2);
    }

    // bobbing fix courtesy of Iris; https://github.com/IrisShaders/Iris/blob/1.20.1/src/main/java/net/coderbot/iris/mixin/MixinModelViewBobbing.java
    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void onRenderLevel(DeltaTracker deltaTracker, CallbackInfo ci) {
        hasShaders = ClientAPI.hasShaderPack();
    }

    @ModifyArg(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobHurt(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"), index = 0)
    private PoseStack renderLevelBobHurt(PoseStack stack) {
        if (hasShaders) return stack;
        stack.pushPose();
        stack.last().pose().identity();
        return stack;
    }


    @Override @Intrinsic
    public double figura$getFov(Camera camera, float tickDelta, boolean changingFov) {
        return this.getFov(camera, tickDelta, changingFov);
    }

    // Don't bob until later
    @WrapOperation(method = "renderLevel",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private void figura$stopBobView(GameRenderer instance, PoseStack stack, float f, Operation<GameRenderer> original) {
        Avatar avatar = AvatarManager.getAvatar(this.minecraft.getCameraEntity() == null ? this.minecraft.player : this.minecraft.getCameraEntity());
        if (!RenderUtils.vanillaModelAndScript(avatar) || !hasShaders)
            original.call(instance, stack, f);
    }

    @WrapOperation(method = "renderLevel",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;bobHurt(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private void figura$stopBobHurt(GameRenderer instance, PoseStack stack, float f, Operation<GameRenderer> original) {
        Avatar avatar = AvatarManager.getAvatar(this.minecraft.getCameraEntity() == null ? this.minecraft.player : this.minecraft.getCameraEntity());
        if (!RenderUtils.vanillaModelAndScript(avatar) || !hasShaders)
            original.call(instance, stack, f);
    }
}
