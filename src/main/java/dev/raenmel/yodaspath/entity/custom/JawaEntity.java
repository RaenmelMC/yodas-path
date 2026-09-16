package dev.raenmel.yodaspath.entity.custom;

import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JawaEntity extends PathAwareEntity {

    public JawaEntity(
            EntityType<? extends PathAwareEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createJawaAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(
                        EntityAttributes.GENERIC_MAX_HEALTH,
                        20.0
                )
                .add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        0.25
                )
                .add(
                        EntityAttributes.GENERIC_FOLLOW_RANGE,
                        24.0
                );
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(
                0,
                new SwimGoal(this)
        );

        this.goalSelector.add(
                1,
                new EscapeDangerGoal(
                        this,
                        1.5
                )
        );

        this.goalSelector.add(
                2,
                new dev.raenmel.yodaspath.entity.ai.FollowScrapMetalGoal(
                        this,
                        1.1,
                        12.0
                )
        );

        this.goalSelector.add(
                3,
                new WanderAroundFarGoal(
                        this,
                        0.8
                )
        );

        this.goalSelector.add(
                4,
                new LookAtEntityGoal(
                        this,
                        PlayerEntity.class,
                        8.0F
                )
        );

        this.goalSelector.add(
                5,
                new LookAroundGoal(this)
        );
    }

    @Override
    protected ActionResult interactMob(
            PlayerEntity player,
            Hand hand
    ) {
        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isOf(ModItems.SCRAP_METAL)) {
            return super.interactMob(player, hand);
        }

        if (this.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return ActionResult.PASS;
        }

        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        ItemStack reward = getBarteringReward();

        ItemEntity itemEntity = new ItemEntity(
                serverWorld,
                this.getX(),
                this.getY() + 0.5,
                this.getZ(),
                reward
        );

        itemEntity.setVelocity(
                (this.random.nextDouble() - 0.5) * 0.15,
                0.2,
                (this.random.nextDouble() - 0.5) * 0.15
        );

        serverWorld.spawnEntity(itemEntity);

        return ActionResult.SUCCESS;
    }

    private ItemStack getBarteringReward() {

        int roll = this.random.nextInt(100);

        if (roll < 30) {
            return new ItemStack(
                    Items.IRON_NUGGET,
                    2 + this.random.nextInt(5)
            );
        }

        if (roll < 55) {
            return new ItemStack(
                    Items.REDSTONE,
                    1 + this.random.nextInt(4)
            );
        }

        if (roll < 75) {
            return new ItemStack(
                    Items.COAL,
                    1 + this.random.nextInt(3)
            );
        }

        if (roll < 87) {
            return new ItemStack(
                    Items.IRON_INGOT,
                    1 + this.random.nextInt(2)
            );
        }

        if (roll < 95) {
            return new ItemStack(
                    ModItems.BLASTER_CELL,
                    1
            );
        }

        if (roll < 99) {
            return new ItemStack(
                    Items.GOLD_INGOT,
                    1
            );
        }

        return new ItemStack(
                Items.EMERALD,
                1
        );
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.JAWA_AMBIENT;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 160;
    }

    public static boolean canSpawn(
            EntityType<JawaEntity> type,
            ServerWorldAccess world,
            SpawnReason spawnReason,
            BlockPos pos,
            Random random
    ) {
        return world.getBlockState(pos.down()).isSolidBlock(
                world,
                pos.down()
        )
                && world.getBlockState(pos).isAir()
                && world.getBlockState(pos.up()).isAir();
    }
}