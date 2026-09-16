package dev.raenmel.yodaspath.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class TatooineCrateScreenHandler extends ScreenHandler {

    private static final int CRATE_SIZE = 18;

    private final Inventory inventory;

    // Utilisé côté client
    public TatooineCrateScreenHandler(
            int syncId,
            PlayerInventory playerInventory
    ) {
        this(
                syncId,
                playerInventory,
                new SimpleInventory(CRATE_SIZE)
        );
    }

    public TatooineCrateScreenHandler(
            int syncId,
            PlayerInventory playerInventory,
            Inventory inventory
    ) {
        super(
                ModScreenHandlers.TATOOINE_CRATE,
                syncId
        );

        checkSize(inventory, CRATE_SIZE);

        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);


        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 9; column++) {

                this.addSlot(
                        new Slot(
                                inventory,
                                column + row * 9,
                                8 + column * 18,
                                18 + row * 18
                        )
                );
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {

                this.addSlot(
                        new Slot(
                                playerInventory,
                                column + row * 9 + 9,
                                8 + column * 18,
                                68 + row * 18
                        )
                );
            }
        }

        for (int column = 0; column < 9; column++) {

            this.addSlot(
                    new Slot(
                            playerInventory,
                            column,
                            8 + column * 18,
                            126
                    )
            );
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(
            PlayerEntity player,
            int slotIndex
    ) {
        ItemStack result = ItemStack.EMPTY;

        Slot slot = this.slots.get(slotIndex);

        if (slot.hasStack()) {

            ItemStack stack = slot.getStack();
            result = stack.copy();


            if (slotIndex < CRATE_SIZE) {


                if (!this.insertItem(
                        stack,
                        CRATE_SIZE,
                        this.slots.size(),
                        true
                )) {
                    return ItemStack.EMPTY;
                }

            } else {

                if (!this.insertItem(
                        stack,
                        0,
                        CRATE_SIZE,
                        false
                )) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (stack.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(
                    player,
                    stack
            );
        }

        return result;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);

        inventory.onClose(player);
    }
}