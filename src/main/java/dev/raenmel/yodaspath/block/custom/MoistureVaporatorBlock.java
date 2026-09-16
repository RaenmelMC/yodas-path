package dev.raenmel.yodaspath.block.custom;

import com.mojang.serialization.MapCodec;
import dev.raenmel.yodaspath.block.entity.ModBlockEntities;
import dev.raenmel.yodaspath.block.entity.MoistureVaporatorBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MoistureVaporatorBlock extends BlockWithEntity {

    public static final MapCodec<MoistureVaporatorBlock> CODEC =
            createCodec(MoistureVaporatorBlock::new);

    public MoistureVaporatorBlock(Settings settings) {
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
        return new MoistureVaporatorBlockEntity(
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world,
            BlockState state,
            BlockEntityType<T> type
    ) {
        if (world.isClient) {
            return null;
        }

        return validateTicker(
                type,
                ModBlockEntities.MOISTURE_VAPORATOR,
                MoistureVaporatorBlockEntity::tick
        );
    }
}