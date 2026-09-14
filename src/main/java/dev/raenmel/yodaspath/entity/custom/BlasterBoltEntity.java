package dev.raenmel.yodaspath.entity.custom;

import dev.raenmel.yodaspath.entity.ModEntities;
import dev.raenmel.yodaspath.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.particle.DustParticleEffect;
import org.joml.Vector3f;
import net.minecraft.server.world.ServerWorld;

public class BlasterBoltEntity
        extends ProjectileEntity
        implements FlyingItemEntity {

    public BlasterBoltEntity(
            EntityType<? extends BlasterBoltEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public BlasterBoltEntity(
            World world,
            LivingEntity owner
    ) {
        this(ModEntities.BLASTER_BOLT, world);

        setOwner(owner);

        setPosition(
                owner.getX(),
                owner.getEyeY() - 0.1D,
                owner.getZ()
        );
    }

    @Override
    protected void initDataTracker(
            net.minecraft.entity.data.DataTracker.Builder builder
    ) {
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(ModItems.BLASTER_BOLT);
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitResult =
                ProjectileUtil.getCollision(
                        this,
                        this::canHit
                );

        if (hitResult.getType() != HitResult.Type.MISS) {
            onCollision(hitResult);
        }

        setPosition(
                getX() + getVelocity().x,
                getY() + getVelocity().y,
                getZ() + getVelocity().z
        );

        if (age > 100) {
            discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!getWorld().isClient) {
            entityHitResult.getEntity().damage(
                    getDamageSources().thrown(
                            this,
                            getOwner()
                    ),
                    6.0F
            );

            spawnImpactParticles();

            discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!getWorld().isClient
                && hitResult.getType() == HitResult.Type.BLOCK) {

            spawnImpactParticles();

            discard();
        }
    }

    private void spawnImpactParticles() {
        if (!(getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }

        DustParticleEffect redParticle =
                new DustParticleEffect(
                        new Vector3f(1.0F, 0.5F, 0.2F),
                        1.0F
                );

        serverWorld.spawnParticles(
                redParticle,
                getX(),
                getY(),
                getZ(),
                12,
                0.15,
                0.15,
                0.15,
                0.08
        );
    }
}