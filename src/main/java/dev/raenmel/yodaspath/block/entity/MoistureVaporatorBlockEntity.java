package dev.raenmel.yodaspath.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoistureVaporatorBlockEntity extends BlockEntity {

    public static final int MAX_WATER = 1000;

    private int waterAmount = 0;
    private int productionTimer = 0;
    private int particleTimer = 0;

    public MoistureVaporatorBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.MOISTURE_VAPORATOR,
                pos,
                state
        );
    }

    public static void tick(
            World world,
            BlockPos pos,
            BlockState state,
            MoistureVaporatorBlockEntity blockEntity
    ) {
        if (world.isClient) {
            return;
        }

        BlockPos skyCheckPos = pos.up();

        if (!world.isSkyVisible(skyCheckPos)) {
            blockEntity.productionTimer = 0;
            blockEntity.particleTimer = 0;
            return;
        }

        if (blockEntity.waterAmount < MAX_WATER) {
            blockEntity.productionTimer++;

            if (blockEntity.productionTimer >= 10) {
                blockEntity.productionTimer = 0;

                blockEntity.waterAmount += 20;

                if (blockEntity.waterAmount > MAX_WATER) {
                    blockEntity.waterAmount = MAX_WATER;
                }

                blockEntity.markDirty();
            }

            return;
        }

        blockEntity.particleTimer++;

        if (blockEntity.particleTimer >= 10) {
            blockEntity.particleTimer = 0;

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.SPLASH,
                        pos.getX() + 0.5,
                        pos.getY() + 2.1,
                        pos.getZ() + 0.5,
                        4,
                        0.20,
                        0.10,
                        0.20,
                        0.08
                );
            }
        }
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int amount) {
        this.waterAmount = Math.max(
                0,
                Math.min(amount, MAX_WATER)
        );

        markDirty();
    }

    @Override
    protected void writeNbt(
            NbtCompound nbt,
            RegistryWrapper.WrapperLookup registryLookup
    ) {
        super.writeNbt(nbt, registryLookup);

        nbt.putInt(
                "water_amount",
                waterAmount
        );
    }

    @Override
    protected void readNbt(
            NbtCompound nbt,
            RegistryWrapper.WrapperLookup registryLookup
    ) {
        super.readNbt(nbt, registryLookup);

        waterAmount = Math.max(
                0,
                Math.min(
                        nbt.getInt("water_amount"),
                        MAX_WATER
                )
        );
    }
}