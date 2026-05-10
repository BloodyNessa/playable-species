package nessa.plsp.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * A minimal multi-page screen with three pages: Human, Merling, Blaze.
 * Uses the reference pattern from the in-repo docs: Button.builder(...).bounds(...).build().
 */
public class SpeciesSelectionScreen extends Screen {
    private static final List<Component> PAGES = List.of(
            Component.literal("Human"),
            Component.literal("Merling"),
            Component.literal("Blaze")
    );

    private final int pageIndex;

    public SpeciesSelectionScreen() {
        this(0);
    }

    private SpeciesSelectionScreen(int pageIndex) {
        super(Component.literal("Species Selection"));
        this.pageIndex = Math.floorMod(pageIndex, PAGES.size());
    }

    @Override
    protected void init() {
        // panel and layout
        int panelW = 220;
        int panelH = 120;
        int left = (this.width - panelW) / 2;
        int top = (this.height - panelH) / 2;

        int btnW = 60;
        int btnH = 20;
        int spacing = 10;

        int centerX = this.width / 2;
        int buttonY = top + panelH - 30; // place buttons near bottom of the panel

        // Left arrow
        this.addRenderableWidget(Button.builder(Component.literal("<"), btn -> this.minecraft.setScreenAndShow(new SpeciesSelectionScreen(pageIndex - 1))).bounds(centerX - btnW - spacing - btnW / 2, buttonY, btnW, btnH).build());

        // Confirm (center)
        this.addRenderableWidget(Button.builder(Component.literal("Confirm"), btn -> this.minecraft.setScreenAndShow(null)).bounds(centerX - btnW / 2, buttonY, btnW, btnH).build());

        // Right arrow
        this.addRenderableWidget(Button.builder(Component.literal(">"), btn -> this.minecraft.setScreenAndShow(new SpeciesSelectionScreen(pageIndex + 1))).bounds(centerX + btnW / 2 + spacing, buttonY, btnW, btnH).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
    }

    @Override
    public void extractMenuBackground(GuiGraphicsExtractor graphics) {
        // dim the background slightly
        graphics.fill(0, 0, this.width, this.height, 0x88000000);

        // panel (larger)
        int panelW = 320;
        int panelH = 180;
        int left = (this.width - panelW) / 2;
        int top = (this.height - panelH) / 2;

        // draw standard menu background (includes rounded corners)
        this.extractMenuBackground(graphics, left, top, panelW, panelH);

        // headline centered inside panel
        int hy = top + 14;
        graphics.centeredText(this.font, PAGES.get(pageIndex), left + panelW / 2, hy, 0xFFFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
