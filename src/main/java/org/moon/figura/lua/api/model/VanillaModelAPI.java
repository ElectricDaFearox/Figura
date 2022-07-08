package org.moon.figura.lua.api.model;

import org.moon.figura.avatars.model.ParentType;
import org.moon.figura.avatars.model.VanillaModelProvider;
import org.moon.figura.lua.LuaNotNil;
import org.moon.figura.lua.LuaWhitelist;
import org.moon.figura.lua.docs.LuaFieldDoc;
import org.moon.figura.lua.docs.LuaTypeDoc;
import org.moon.figura.lua.types.LuaPairsIterator;

import java.util.List;

@LuaWhitelist
@LuaTypeDoc(
        name = "VanillaModelAPI",
        description = "vanilla_model"
)
public class VanillaModelAPI {

    // -- body -- //

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.head")
    public final VanillaModelPart HEAD;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.body")
    public final VanillaModelPart BODY;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.left_arm")
    public final VanillaModelPart LEFT_ARM;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.right_arm")
    public final VanillaModelPart RIGHT_ARM;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.left_leg")
    public final VanillaModelPart LEFT_LEG;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.right_leg")
    public final VanillaModelPart RIGHT_LEG;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.hat")
    public final VanillaModelPart HAT;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.jacket")
    public final VanillaModelPart JACKET;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.left_sleeve")
    public final VanillaModelPart LEFT_SLEEVE;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.right_sleeve")
    public final VanillaModelPart RIGHT_SLEEVE;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.left_pants")
    public final VanillaModelPart LEFT_PANTS;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.right_pants")
    public final VanillaModelPart RIGHT_PANTS;

    // -- cape -- //

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.cape_model")
    public final VanillaModelPart CAPE_MODEL;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.fake_cape")
    public final VanillaModelPart FAKE_CAPE;

    // -- armor -- //

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.helmet_head")
    public final VanillaModelPart HELMET_HEAD;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.helmet_hat")
    public final VanillaModelPart HELMET_HAT;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.chestplate_body")
    public final VanillaModelPart CHESTPLATE_BODY;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.chestplate_left_arm")
    public final VanillaModelPart CHESTPLATE_LEFT_ARM;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.chestplate_right_arm")
    public final VanillaModelPart CHESTPLATE_RIGHT_ARM;


    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.leggings_body")
    public final VanillaModelPart LEGGINGS_BODY;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.leggings_left_leg")
    public final VanillaModelPart LEGGINGS_LEFT_LEG;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.leggings_right_leg")
    public final VanillaModelPart LEGGINGS_RIGHT_LEG;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.boots_left_leg")
    public final VanillaModelPart BOOTS_LEFT_LEG;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.boots_right_leg")
    public final VanillaModelPart BOOTS_RIGHT_LEG;

    // -- elytra -- //

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.left_elytra")
    public final VanillaModelPart LEFT_ELYTRA;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.right_elytra")
    public final VanillaModelPart RIGHT_ELYTRA;

    // -- held items -- //

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.left_item")
    public final VanillaModelPart LEFT_ITEM;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.right_item")
    public final VanillaModelPart RIGHT_ITEM;


    // -- groups -- //


    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.all")
    public final VanillaGroupPart ALL;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.player")
    public final VanillaGroupPart PLAYER;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.outer_layer")
    public final VanillaGroupPart OUTER_LAYER;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.inner_layer")
    public final VanillaGroupPart INNER_LAYER;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.cape")
    public final VanillaGroupPart CAPE;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.armor")
    public final VanillaGroupPart ARMOR;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.helmet")
    public final VanillaGroupPart HELMET;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.chestplate")
    public final VanillaGroupPart CHESTPLATE;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.leggings")
    public final VanillaGroupPart LEGGINGS;
    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.boots")
    public final VanillaGroupPart BOOTS;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.elytra")
    public final VanillaGroupPart ELYTRA;

    @LuaWhitelist
    @LuaFieldDoc(description = "vanilla_model.held_items")
    public final VanillaGroupPart HELD_ITEMS;

    public VanillaModelAPI() {
        // -- body -- //

        HEAD = new VanillaModelPart(ParentType.Head, VanillaModelProvider.HEAD.func);
        BODY = new VanillaModelPart(ParentType.Body, VanillaModelProvider.BODY.func);
        LEFT_ARM = new VanillaModelPart(ParentType.LeftArm, VanillaModelProvider.LEFT_ARM.func);
        RIGHT_ARM = new VanillaModelPart(ParentType.RightArm, VanillaModelProvider.RIGHT_ARM.func);
        LEFT_LEG = new VanillaModelPart(ParentType.LeftLeg, VanillaModelProvider.LEFT_LEG.func);
        RIGHT_LEG = new VanillaModelPart(ParentType.RightLeg, VanillaModelProvider.RIGHT_LEG.func);

        HAT = new VanillaModelPart(ParentType.Head, VanillaModelProvider.HAT.func);
        JACKET = new VanillaModelPart(ParentType.Body, VanillaModelProvider.JACKET.func);
        LEFT_SLEEVE = new VanillaModelPart(ParentType.LeftArm, VanillaModelProvider.LEFT_SLEEVE.func);
        RIGHT_SLEEVE = new VanillaModelPart(ParentType.RightArm, VanillaModelProvider.RIGHT_SLEEVE.func);
        LEFT_PANTS = new VanillaModelPart(ParentType.LeftLeg, VanillaModelProvider.LEFT_PANTS.func);
        RIGHT_PANTS = new VanillaModelPart(ParentType.RightLeg, VanillaModelProvider.RIGHT_PANTS.func);

        // -- cape -- //

        CAPE_MODEL = new VanillaModelPart(ParentType.Cape, VanillaModelProvider.CAPE.func);
        FAKE_CAPE = new VanillaModelPart(ParentType.Cape, VanillaModelProvider.FAKE_CAPE.func);

        // -- armor -- //

        HELMET_HEAD = new VanillaModelPart(ParentType.Head, VanillaModelProvider.HEAD.func);
        HELMET_HAT = new VanillaModelPart(ParentType.Head, VanillaModelProvider.HAT.func);

        CHESTPLATE_BODY = new VanillaModelPart(ParentType.Body, VanillaModelProvider.BODY.func);
        CHESTPLATE_LEFT_ARM = new VanillaModelPart(ParentType.LeftArm, VanillaModelProvider.LEFT_ARM.func);
        CHESTPLATE_RIGHT_ARM = new VanillaModelPart(ParentType.RightArm, VanillaModelProvider.RIGHT_ARM.func);

        LEGGINGS_BODY = new VanillaModelPart(ParentType.Body, VanillaModelProvider.BODY.func);
        LEGGINGS_LEFT_LEG = new VanillaModelPart(ParentType.LeftLeg, VanillaModelProvider.LEFT_LEG.func);
        LEGGINGS_RIGHT_LEG = new VanillaModelPart(ParentType.RightLeg, VanillaModelProvider.RIGHT_LEG.func);

        BOOTS_LEFT_LEG = new VanillaModelPart(ParentType.LeftLeg, VanillaModelProvider.LEFT_LEG.func);
        BOOTS_RIGHT_LEG = new VanillaModelPart(ParentType.RightLeg, VanillaModelProvider.RIGHT_LEG.func);

        // -- elytra -- //

        LEFT_ELYTRA = new VanillaModelPart(ParentType.LeftElytra, VanillaModelProvider.LEFT_ELYTRON.func);
        RIGHT_ELYTRA = new VanillaModelPart(ParentType.RightElytra, VanillaModelProvider.RIGHT_ELYTRON.func);

        // -- held items -- //

        LEFT_ITEM = new VanillaModelPart(ParentType.LeftArm, null);
        RIGHT_ITEM = new VanillaModelPart(ParentType.RightArm, null);


        // -- groups -- //


        INNER_LAYER = new VanillaGroupPart(HEAD, BODY, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG);
        OUTER_LAYER = new VanillaGroupPart(HAT, JACKET, LEFT_SLEEVE, RIGHT_SLEEVE, LEFT_PANTS, RIGHT_PANTS);
        PLAYER = new VanillaGroupPart(INNER_LAYER, OUTER_LAYER);

        CAPE = new VanillaGroupPart(CAPE_MODEL, FAKE_CAPE);

        HELMET = new VanillaGroupPart(HELMET_HEAD, HELMET_HAT);
        CHESTPLATE = new VanillaGroupPart(CHESTPLATE_BODY, CHESTPLATE_LEFT_ARM, CHESTPLATE_RIGHT_ARM);
        LEGGINGS = new VanillaGroupPart(LEGGINGS_BODY, LEGGINGS_LEFT_LEG, LEGGINGS_RIGHT_LEG);
        BOOTS = new VanillaGroupPart(BOOTS_LEFT_LEG, BOOTS_RIGHT_LEG);
        ARMOR = new VanillaGroupPart(HELMET, CHESTPLATE, LEGGINGS, BOOTS);

        ELYTRA = new VanillaGroupPart(LEFT_ELYTRA, RIGHT_ELYTRA);

        HELD_ITEMS = new VanillaGroupPart(LEFT_ITEM, RIGHT_ITEM);

        ALL = new VanillaGroupPart(PLAYER, CAPE, ARMOR, ELYTRA, HELD_ITEMS);
    }

    private static final LuaPairsIterator<VanillaModelAPI, String> PAIRS_ITERATOR =
            new LuaPairsIterator<>(List.of(
                    "HEAD", "BODY", "LEFT_ARM", "RIGHT_ARM", "LEFT_LEG", "RIGHT_LEG",
                    "HAT", "JACKET", "LEFT_SLEEVE", "RIGHT_SLEEVE", "LEFT_PANTS", "RIGHT_PANTS",

                    "CAPE_MODEL", "FAKE_CAPE",

                    "HELMET_HEAD", "HELMET_HAT",
                    "CHESTPLATE_BODY", "CHESTPLATE_LEFT_ARM", "CHESTPLATE_RIGHT_ARM",
                    "LEGGINGS_BODY", "LEGGINGS_LEFT_LEG", "LEGGINGS_RIGHT_LEG",
                    "BOOTS_LEFT_LEG", "BOOTS_RIGHT_LEG",

                    "LEFT_ELYTRA", "RIGHT_ELYTRA",

                    "LEFT_ITEM", "RIGHT_ITEM"
            ), VanillaModelAPI.class, String.class);
    @LuaWhitelist
    public static LuaPairsIterator<VanillaModelAPI, String> __pairs(@LuaNotNil VanillaModelAPI arg) {
        return PAIRS_ITERATOR;
    }

    @Override
    public String toString() {
        return "VanillaModelAPI";
    }
}
