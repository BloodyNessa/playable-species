package nessa.plsp.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * A minimal multi-page screen with three pages: Human, Merling, Blaze.
 * The bottom center button reads "Confirm"; left/right are arrows that switch pages
 * by closing this screen and opening the next/previous page instance.
 */
public class SpeciesSelectionScreen extends Screen {
    private static final List<Component> PAGES = List.of(
            new TextComponent("Human"),
            new TextComponent("Merling"),
            new TextComponent("Blaze")
    );

    private final int pageIndex;

    public SpeciesSelectionScreen() {
        this(0);
    }

    private SpeciesSelectionScreen(int pageIndex) {
        super(new TextComponent("Species Selection"));
        this.pageIndex = Math.floorMod(pageIndex, PAGES.size());
    }

    @Override
    protected void init() {
        super.init();

        int w = this.width;
        int h = this.height;

        int btnW = 40;
        int btnH = 20;
        int spacing = 10;

        int centerX = w / 2;
        int bottomY = h - 40;

        // Left arrow
        this.addRenderableWidget(new Button(centerX - btnW - spacing - btnW/2, bottomY, btnW, btnH, new TextComponent("<"), btn -> {
            // open previous page
            Minecraft.getInstance().setScreen(new SpeciesSelectionScreen(pageIndex - 1));
        }));

        // Confirm (center)
        this.addRenderableWidget(new Button(centerX - btnW/2, bottomY, btnW, btnH, new TextComponent("Confirm"), btn -> {
            // For now just close the screen on confirm
            Minecraft.getInstance().setScreen(null);
        }));

        // Right arrow
        this.addRenderableWidget(new Button(centerX + btnW/2 + spacing, bottomY, btnW, btnH, new TextComponent(">"), btn -> {
            // open next page
            Minecraft.getInstance().setScreen(new SpeciesSelectionScreen(pageIndex + 1));
        }));
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        // Draw title (page headline) at top center
        Component headline = PAGES.get(pageIndex);
        int titleY = 20;
        drawCenteredString(matrices, this.font, headline.getString(), this.width / 2, titleY, 0xFFFFFF);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
