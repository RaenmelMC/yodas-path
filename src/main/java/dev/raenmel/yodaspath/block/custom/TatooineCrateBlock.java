package dev.raenmel.yodaspath.block.custom;

import com.mojang.serialization.MapCodec;
import dev.raenmel.yodaspath.block.entity.TatooineCrateBlockEntity;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TatooineCrateBlock extends BlockWithEntity {

    public static final MapCodec<TatooineCrateBlock> CODEC =
            createCodec(TatooineCrateBlock::new);

    public TatooineCrateBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new TatooineCrateBlockEntity(
                pos,
                state
        );
    }

    @Override
    protected BlockRenderType getRenderType(
            BlockState state
    ) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit
    ) {
        if (!world.isClient) {

            NamedScreenHandlerFactory factory =
                    state.createScreenHandlerFactory(
                            world,
                            pos
                    );

            if (factory != null) {
                player.openHandledScreen(factory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void onStateReplaced(
            BlockState state,
            World world,
            BlockPos pos,
            BlockState newState,
            boolean moved
    ) {
        if (!state.isOf(newState.getBlock())) {

            BlockEntity blockEntity =
                    world.getBlockEntity(pos);

            if (blockEntity instanceof TatooineCrateBlockEntity crate) {

                ItemScatterer.spawn(
                        world,
                        pos,
                        crate
                );

                world.updateComparators(
                        pos,
                        this
                );
            }

            super.onStateReplaced(
                    state,
                    world,
                    pos,
                    newState,
                    moved
            );
        }
    }
}