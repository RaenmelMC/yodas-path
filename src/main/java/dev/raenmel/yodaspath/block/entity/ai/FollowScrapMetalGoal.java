package dev.raenmel.yodaspath.entity.ai;

import dev.raenmel.yodaspath.entity.custom.JawaEntity;
import dev.raenmel.yodaspath.item.ModItems;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public class FollowScrapMetalGoal extends Goal {

    private final JawaEntity jawa;
    private final double speed;
    private final double detectionRange;

    private PlayerEntity targetPlayer;

    public FollowScrapMetalGoal(
            JawaEntity jawa,
            double speed,
            double detectionRange
    ) {
        this.jawa = jawa;
        this.speed = speed;
        this.detectionRange = detectionRange;

        this.setControls(
                EnumSet.of(
                        Control.MOVE,
                        Control.LOOK
                )
        );
    }

    @Override
    public boolean canStart() {

        this.targetPlayer =
                this.jawa.getWorld().getClosestPlayer(
                        this.jawa,
                        detectionRange
                );

        return isHoldingScrapMetal(this.targetPlayer);
    }

    @Override
    public boolean shouldContinue() {

        if (this.targetPlayer == null) {
            return false;
        }

        if (!this.targetPlayer.isAlive()) {
            return false;
        }

        if (!isHoldingScrapMetal(this.targetPlayer)) {
            return false;
        }

        return this.jawa.squaredDistanceTo(this.targetPlayer)
                <= detectionRange * detectionRange;
    }

    @Override
    public void tick() {

        if (this.targetPlayer == null) {
            return;
        }

        this.jawa.getLookControl().lookAt(
                this.targetPlayer,
                30.0F,
                30.0F
        );

        double distanceSquared =
                this.jawa.squaredDistanceTo(this.targetPlayer);

        if (distanceSquared > 4.0) {
            this.jawa.getNavigation().startMovingTo(
                    this.targetPlayer,
                    speed
            );
        } else {
            this.jawa.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.targetPlayer = null;
        this.jawa.getNavigation().stop();
    }

    private boolean isHoldingScrapMetal(PlayerEntity player) {

        if (player == null) {
            return false;
        }

        ItemStack mainHand =
                player.getMainHandStack();

        ItemStack offHand =
                player.getOffHandStack();

        return mainHand.isOf(ModItems.SCRAP_METAL)
                || offHand.isOf(ModItems.SCRAP_METAL);
    }
}