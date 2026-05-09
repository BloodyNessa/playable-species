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
        // layout
        int btnW = 60;
        int btnH = 20;
        int spacing = 10;

        int centerX = this.width / 2;
        int bottomY = this.height - 40;

        // Left arrow
        this.addRenderableWidget(Button.builder(Component.literal("<"), btn -> this.minecraft.setScreenAndShow(new SpeciesSelectionScreen(pageIndex - 1))).bounds(centerX - btnW - spacing - btnW / 2, bottomY, btnW, btnH).build());

        // Confirm (center)
        this.addRenderableWidget(Button.builder(Component.literal("Confirm"), btn -> this.minecraft.setScreenAndShow(null)).bounds(centerX - btnW / 2, bottomY, btnW, btnH).build());

        // Right arrow
        this.addRenderableWidget(Button.builder(Component.literal(">"), btn -> this.minecraft.setScreenAndShow(new SpeciesSelectionScreen(pageIndex + 1))).bounds(centerX + btnW / 2 + spacing, bottomY, btnW, btnH).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        String headline = PAGES.get(pageIndex).getString();
        int x = (this.width - this.font.width(headline)) / 2;
        int y = 20;
        graphics.text(this.font, headline, x, y, 0xFFFFFFFF, true);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
