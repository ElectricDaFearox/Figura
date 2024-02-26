package org.figuramc.figura.gui.widgets.lists;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.gui.widgets.FiguraWidget;
import org.figuramc.figura.gui.widgets.SwitchButton;
import org.figuramc.figura.gui.widgets.TextField;
import org.figuramc.figura.utils.FiguraText;
import org.figuramc.figura.utils.ui.UIHelper;
import org.figuramc.figura.wizards.AvatarWizard;
import org.figuramc.figura.wizards.WizardEntry;

import java.util.*;

public class AvatarWizardList extends AbstractList {

    private final AvatarWizard wizard;
    private final Map<Component, List<GuiEventListener>> map = new LinkedHashMap<>();

    public AvatarWizardList(int x, int y, int width, int height, AvatarWizard wizard) {
        super(x, y, width, height);
        this.wizard = wizard;
        generate();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        // background and scissors
        UIHelper.renderSliced(stack, x, y, width, height, UIHelper.OUTLINE_FILL);
        UIHelper.setupScissor(x + scissorsX, y + scissorsY, width + scissorsWidth, height + scissorsHeight);

        // scrollbar
        Font font = Minecraft.getInstance().font;
        int lineHeight = font.lineHeight + 8;
        int entryHeight = 24;

        int size = 0;
        for (List<GuiEventListener> list : map.values()) {
            for (GuiEventListener widget : list) {
                if (widget instanceof WizardInputBox) {
                    WizardInputBox ib = (WizardInputBox) widget;
                    ib.setVisible(wizard.checkDependency(ib.entry));
                    if (ib.isVisible()) size++;
                } else if (widget instanceof WizardToggleButton) {
                    WizardToggleButton tb = (WizardToggleButton) widget;
                    tb.setVisible(wizard.checkDependency(tb.entry));
                    if (tb.isVisible()) size++;
                }
            }
        }
        int totalHeight = entryHeight * size + lineHeight * map.size();

        scrollBar.setVisible(totalHeight > height);
        scrollBar.setScrollRatio(entryHeight, totalHeight - height);

        // render list
        int yOffset = scrollBar.isVisible() ? (int) -(Mth.lerp(scrollBar.getScrollProgress(), -4, totalHeight - height)) : 4;
        for (Map.Entry<Component, List<GuiEventListener>> entry : map.entrySet()) {
            List<GuiEventListener> value = entry.getValue();
            if (value.isEmpty())
                continue;

            int newY = yOffset + lineHeight;
            // elements
            for (GuiEventListener w : entry.getValue()) {
                FiguraWidget widget = (FiguraWidget) w;
                if (widget.isVisible()) {
                    widget.setY(y + newY);
                    newY += entryHeight;
                }
            }

            if (newY == yOffset + lineHeight)
                continue;

            // category
            UIHelper.drawCenteredString(stack, font, entry.getKey(), x + width / 2, y + yOffset + 4, 0xFFFFFF);
            yOffset = newY;
        }

        // children
        super.render(stack, mouseX, mouseY, delta);

        // reset scissor
        UIHelper.disableScissor();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // fix mojang focusing for text fields
        for (GuiEventListener widget : children()) {
            if (widget instanceof TextField) {
                TextField field = (TextField) widget;
                field.getField().setFocus(field.isEnabled() && field.isMouseOver(mouseX, mouseY));
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void generate() {
        for (List<GuiEventListener> value : map.values())
            children.removeAll(value);
        map.clear();

        int x = this.getX() + getWidth() / 2 + 4;
        int width = this.getWidth() / 2 - 20;

        Component lastName = TextComponent.EMPTY.copy();
        List<GuiEventListener> lastList = new ArrayList<>();

        for (WizardEntry value : WizardEntry.all()) {
            switch (value.type) {
                case CATEGORY:
                    if (!lastList.isEmpty()) {
                        map.put(lastName, lastList);
                        children.addAll(lastList);
                    }
                    lastName = new FiguraText("gui.avatar_wizard." + value.name.toLowerCase(Locale.US));
                    lastList = new ArrayList<>();
                    break;
                case TEXT:
                    lastList.add(new WizardInputBox(x, width, this, value));
                    break;
                case TOGGLE:
                    lastList.add(new WizardToggleButton(x, width, this, value));
                    break;
            }
        }

        map.put(lastName, lastList);
        children.addAll(lastList);
    }

    private static class WizardInputBox extends TextField {

        private final AvatarWizardList parent;
        private final WizardEntry entry;
        private final Component name;

        public WizardInputBox(int x, int width, AvatarWizardList parent, WizardEntry entry) {
            super(x, 0, width, 20, HintType.ANY, s -> parent.wizard.changeEntry(entry, s));
            this.parent = parent;
            this.entry = entry;
            this.name = new FiguraText("gui.avatar_wizard." + entry.name.toLowerCase(Locale.US));
            this.getField().setValue(String.valueOf(parent.wizard.getEntry(entry, "")));
        }

        @Override
        public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
            if (!isVisible()) return;
            super.render(stack, mouseX, mouseY, delta);

            Font font = Minecraft.getInstance().font;
            MutableComponent name = this.name.copy();
            if (!this.getField().getValue().trim().isEmpty())
                name.setStyle(FiguraMod.getAccentColor());
            font.draw(stack, name, getX() - getWidth() - 8, (int) (getY() + (getHeight() - font.lineHeight) / 2f), 0xFFFFFF);
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return this.parent.isInsideScissors(mouseX, mouseY) && super.isMouseOver(mouseX, mouseY);
        }
    }

    private static class WizardToggleButton extends SwitchButton {

        private final AvatarWizardList parent;
        private final WizardEntry entry;

        public WizardToggleButton(int x, int width, AvatarWizardList parent, WizardEntry entry) {
            super(x, 0, width, 20, new FiguraText("gui.avatar_wizard." + entry.name.toLowerCase(Locale.US)), false);
            this.parent = parent;
            this.entry = entry;
            this.setToggled((boolean) parent.wizard.getEntry(entry, false));
        }

        @Override
        public void onPress() {
            super.onPress();
            parent.wizard.changeEntry(entry, this.isToggled());
        }

        @Override
        protected void renderDefaultTexture(PoseStack stack, float delta) {
            // button
            stack.pushPose();
            stack.translate(getWidth() - 30, 0, 0);
            super.renderDefaultTexture(stack, delta);
            stack.popPose();
        }

        @Override
        protected void renderText(PoseStack stack, float delta) {
            // name
            Font font = Minecraft.getInstance().font;
            MutableComponent name = getMessage().copy();
            if (this.isToggled())
                name.withStyle(FiguraMod.getAccentColor());
            font.draw(stack, name, getX() - getWidth() - 8, (int) (getY() + (getHeight() - font.lineHeight) / 2f), 0xFFFFFF);
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return this.parent.isInsideScissors(mouseX, mouseY) && super.isMouseOver(mouseX, mouseY);
        }
    }
}
