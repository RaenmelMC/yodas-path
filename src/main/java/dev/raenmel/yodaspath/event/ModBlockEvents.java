package dev.raenmel.yodaspath.event;

import dev.raenmel.yodaspath.block.ModBlocks;
import dev.raenmel.yodaspath.block.entity.MoistureVaporatorBlockEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class ModBlockEvents {

    public static void initialize() {

        UseBlockCallback.EVENT.register(
                (player, world, hand, hitResult) -> {

                    if (!world.getBlockState(hitResult.getBlockPos())
                            .isOf(ModBlocks.MOISTURE_VAPORATOR)) {
                        return ActionResult.PASS;
                    }

                    BlockEntity blockEntity =
                            world.getBlockEntity(
                                    hitResult.getBlockPos()
                            );

                    if (!(blockEntity instanceof MoistureVaporatorBlockEntity vaporator)) {
                        return ActionResult.PASS;
                    }

                    ItemStack stack =
                            player.getStackInHand(hand);

                    if (world.isClient) {
                        if (stack.isOf(Items.BUCKET)) {
                            return ActionResult.SUCCESS;
                        }

                        return ActionResult.PASS;
                    }

                    player.sendMessage(
                            Text.literal(
                                    "Moisture Vaporator — "
                                            + vaporator.getWaterAmount()
                                            + " / "
                                            + MoistureVaporatorBlockEntity.MAX_WATER
                                            + " mB"
                            ),
                            true
                    );

                    if (!stack.isOf(Items.BUCKET)) {
                        return ActionResult.SUCCESS;
                    }

                    if (vaporator.getWaterAmount()
                            < MoistureVaporatorBlockEntity.MAX_WATER) {
                        return ActionResult.SUCCESS;
                    }

                    vaporator.setWaterAmount(0);

                    if (stack.getCount() == 1) {

                        player.setStackInHand(
                                hand,
                                new ItemStack(Items.WATER_BUCKET)
                        );

                    } else {

                        stack.decrement(1);

                        ItemStack waterBucket =
                                new ItemStack(Items.WATER_BUCKET);

                        if (!player.getInventory()
                                .insertStack(waterBucket)) {

                            player.dropItem(
                                    waterBucket,
                                    false
                            );
                        }
                    }

                    return ActionResult.SUCCESS;
                }
        );
    }
}