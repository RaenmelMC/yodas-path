package dev.raenmel.yodaspath.item.custom;

import dev.raenmel.yodaspath.entity.custom.BlasterBoltEntity;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BlasterItem extends Item {

    public BlasterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(
            World world,
            PlayerEntity user,
            Hand hand
    ) {
        ItemStack blasterStack = user.getStackInHand(hand);

        ItemStack cellStack = findBlasterCell(user);

        if (cellStack == null && !user.getAbilities().creativeMode) {
            return TypedActionResult.fail(blasterStack);
        }

        if (!world.isClient) {
            BlasterBoltEntity bolt =
                    new BlasterBoltEntity(world, user);

            bolt.setVelocity(
                    user,
                    user.getPitch(),
                    user.getYaw(),
                    0.0F,
                    3.0F,
                    0.0F
            );

            world.spawnEntity(bolt);

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    ModSounds.BLASTER_SHOOT,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            if (!user.getAbilities().creativeMode && cellStack != null) {
                int newDamage = cellStack.getDamage() + 1;

                if (newDamage >= cellStack.getMaxDamage()) {
                    cellStack.decrement(1);
                } else {
                    cellStack.setDamage(newDamage);
                }
            }

            user.getItemCooldownManager().set(
                    this,
                    6
            );
        }

        return TypedActionResult.success(
                blasterStack,
                world.isClient()
        );
    }

    private ItemStack findBlasterCell(PlayerEntity player) {

        for (int i = 0; i < player.getInventory().size(); i++) {

            ItemStack stack =
                    player.getInventory().getStack(i);

            if (stack.isOf(ModItems.BLASTER_CELL)) {
                return stack;
            }
        }

        return null;
    }
}