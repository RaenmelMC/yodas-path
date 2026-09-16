package dev.raenmel.yodaspath.client.screen;

import dev.raenmel.yodaspath.screen.TatooineCrateScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TatooineCrateScreen
        extends HandledScreen<TatooineCrateScreenHandler> {

    private static final Identifier TEXTURE =
            Identifier.ofVanilla(
                    "textures/gui/container/generic_54.png"
            );

    private final int rows;

    public TatooineCrateScreen(
            TatooineCrateScreenHandler handler,
            PlayerInventory inventory,
            Text title
    ) {
        super(handler, inventory, title);

        this.rows = 2;

        this.backgroundHeight =
                114 + rows * 18;

        this.playerInventoryTitleY =
                backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(
            DrawContext context,
            float delta,
            int mouseX,
            int mouseY
    ) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Partie supérieure :
        // les deux rangées de la Tatooine Crate
        context.drawTexture(
                TEXTURE,
                x,
                y,
                0,
                0,
                backgroundWidth,
                rows * 18 + 17,
                256,
                256
        );

        // Partie inférieure :
        // inventaire + hotbar du joueur
        context.drawTexture(
                TEXTURE,
                x,
                y + rows * 18 + 17,
                0,
                126,
                backgroundWidth,
                96,
                256,
                256
        );
    }

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {
        renderBackground(
                context,
                mouseX,
                mouseY,
                delta
        );

        super.render(
                context,
                mouseX,
                mouseY,
                delta
        );

        drawMouseoverTooltip(
                context,
                mouseX,
                mouseY
        );
    }
}