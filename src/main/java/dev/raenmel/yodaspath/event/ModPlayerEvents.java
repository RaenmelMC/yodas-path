package dev.raenmel.yodaspath.event;

import dev.raenmel.yodaspath.entity.custom.BlasterBoltEntity;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.item.custom.LightSaberItem;
import dev.raenmel.yodaspath.sound.ModSounds;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModPlayerEvents {

    private static final double DEFLECT_RADIUS = 3.5D;
    private static final int DEFLECT_WINDOW_TICKS = 8;

    private static final Map<UUID, Integer> DEFLECT_TIMERS =
            new HashMap<>();

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            DEFLECT_TIMERS.replaceAll(
                    (uuid, ticks) -> ticks - 1
            );

            DEFLECT_TIMERS.entrySet().removeIf(
                    entry -> entry.getValue() <= 0
            );

            for (ServerPlayerEntity player
                    : server.getPlayerManager().getPlayerList()) {

                if (!isDeflecting(player)) {
                    continue;
                }

                deflectProjectiles(player);
            }
        });
    }

    public static void startDeflect(ServerPlayerEntity player) {
        ItemStack stack = player.getMainHandStack();

        if (!stack.isOf(ModItems.LIGHT_SABER)) {
            return;
        }

        if (!LightSaberItem.isActivated(stack)) {
            return;
        }

        DEFLECT_TIMERS.put(
                player.getUuid(),
                DEFLECT_WINDOW_TICKS
        );
    }

    private static boolean isDeflecting(
            ServerPlayerEntity player
    ) {
        return DEFLECT_TIMERS.containsKey(
                player.getUuid()
        );
    }

    private static void deflectProjectiles(
            ServerPlayerEntity player
    ) {
        ServerWorld world = player.getServerWorld();

        Box searchBox = player.getBoundingBox()
                .expand(DEFLECT_RADIUS);

        List<ProjectileEntity> projectiles =
                world.getEntitiesByClass(
                        ProjectileEntity.class,
                        searchBox,
                        projectile ->
                                projectile.isAlive()
                                        && (
                                        projectile instanceof PersistentProjectileEntity
                                                || projectile instanceof BlasterBoltEntity
                                )
                );

        Vec3d lookDirection =
                player.getRotationVec(1.0F).normalize();

        for (ProjectileEntity projectile : projectiles) {

            Vec3d velocity =
                    projectile.getVelocity();

            if (velocity.lengthSquared() < 0.001D) {
                continue;
            }

            Vec3d toProjectile =
                    projectile.getPos()
                            .subtract(player.getEyePos())
                            .normalize();

            double facing =
                    lookDirection.dotProduct(toProjectile);

            if (facing < 0.15D) {
                continue;
            }

            Vec3d projectileDirection =
                    velocity.normalize();

            Vec3d towardPlayer =
                    player.getEyePos()
                            .subtract(projectile.getPos())
                            .normalize();

            double incoming =
                    projectileDirection.dotProduct(
                            towardPlayer
                    );

            if (incoming < 0.25D) {
                continue;
            }

            deflectProjectile(
                    player,
                    projectile
            );
        }
    }

    private static void deflectProjectile(
            ServerPlayerEntity player,
            ProjectileEntity projectile
    ) {
        ServerWorld world = player.getServerWorld();

        double speed =
                projectile.getVelocity().length();

        Vec3d newDirection =
                player.getRotationVec(1.0F)
                        .normalize();

        projectile.setVelocity(
                newDirection.multiply(
                        Math.max(speed, 1.5D) * 1.15D
                )
        );

        projectile.velocityModified = true;
        projectile.setOwner(player);

        world.playSound(
                null,
                projectile.getX(),
                projectile.getY(),
                projectile.getZ(),
                ModSounds.LIGHT_SABER_DEFLECT,
                SoundCategory.PLAYERS,
                1.0F,
                1.0F
        );

        world.spawnParticles(
                ParticleTypes.ELECTRIC_SPARK,
                projectile.getX(),
                projectile.getY(),
                projectile.getZ(),
                10,
                0.15,
                0.15,
                0.15,
                0.08
        );
    }
}