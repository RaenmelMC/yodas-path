package dev.raenmel.yodaspath.block.entity;

import dev.raenmel.yodaspath.screen.TatooineCrateScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TatooineCrateBlockEntity
        extends BlockEntity
        implements Inventory, NamedScreenHandlerFactory {

    public static final int INVENTORY_SIZE = 18;

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(
                    INVENTORY_SIZE,
                    ItemStack.EMPTY
            );

    public TatooineCrateBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.TATOOINE_CRATE,
                pos,
                state
        );
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(
                "container.yodaspath.tatooine_crate"
        );
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(
            int syncId,
            PlayerInventory playerInventory,
            PlayerEntity player
    ) {
        return new TatooineCrateScreenHandler(
                syncId,
                playerInventory,
                this
        );
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(
            int slot,
            int amount
    ) {
        ItemStack result =
                Inventories.splitStack(
                        inventory,
                        slot,
                        amount
                );

        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack result =
                Inventories.removeStack(
                        inventory,
                        slot
                );

        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    @Override
    public void setStack(
            int slot,
            ItemStack stack
    ) {
        inventory.set(slot, stack);

        stack.capCount(getMaxCount(stack));

        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (world == null) {
            return false;
        }

        if (world.getBlockEntity(pos) != this) {
            return false;
        }

        return player.squaredDistanceTo(
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5
        ) <= 64.0;
    }

    @Override
    public void clear() {
        inventory.clear();
        markDirty();
    }

    @Override
    protected void writeNbt(
            NbtCompound nbt,
            RegistryWrapper.WrapperLookup registryLookup
    ) {
        super.writeNbt(nbt, registryLookup);

        Inventories.writeNbt(
                nbt,
                inventory,
                registryLookup
        );
    }

    @Override
    protected void readNbt(
            NbtCompound nbt,
            RegistryWrapper.WrapperLookup registryLookup
    ) {
        super.readNbt(nbt, registryLookup);

        Inventories.readNbt(
                nbt,
                inventory,
                registryLookup
        );
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (world != null && !world.isClient) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_CHEST_OPEN,
                    SoundCategory.BLOCKS,
                    0.5F,
                    1.0F
            );
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (world != null && !world.isClient) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_CHEST_CLOSE,
                    SoundCategory.BLOCKS,
                    0.5F,
                    1.0F
            );
        }
    }
}