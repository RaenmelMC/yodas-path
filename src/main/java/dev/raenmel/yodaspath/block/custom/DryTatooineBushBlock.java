package dev.raenmel.yodaspath.block.custom;

import com.mojang.serialization.MapCodec;
import dev.raenmel.yodaspath.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DryTatooineBushBlock extends PlantBlock {

    public static final MapCodec<DryTatooineBushBlock> CODEC =
            createCodec(DryTatooineBushBlock::new);

    public DryTatooineBushBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape SHAPE =
            createCuboidShape(
                    3.0,
                    0.0,
                    3.0,
                    13.0,
                    10.0,
                    13.0
            );

    @Override
    protected VoxelShape getOutlineShape(
            BlockState state,
            BlockView world,
            BlockPos pos,
            net.minecraft.block.ShapeContext context
    ) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlantOnTop(
            BlockState floor,
            BlockView world,
            BlockPos pos
    ) {
        return floor.isOf(ModBlocks.TATOOINE_SAND)
                || floor.isOf(Blocks.SAND);
    }
}