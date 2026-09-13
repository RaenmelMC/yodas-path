package dev.raenmel.yodaspath.item.custom;

import dev.raenmel.yodaspath.component.ModDataComponents;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.item.kyber.KyberColor;
import dev.raenmel.yodaspath.sound.ModSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LightSaberItem extends Item {

    public LightSaberItem(Settings settings) {
        super(settings);
    }

    public static void setKyberColor(ItemStack stack, KyberColor color) {
        stack.set(ModDataComponents.KYBER_COLOR, color.getId());
    }

    public static KyberColor getKyberColor(ItemStack stack) {
        String color = stack.get(ModDataComponents.KYBER_COLOR);

        if (color == null) {
            return null;
        }

        return KyberColor.fromId(color);
    }

    public static boolean hasKyberCrystal(ItemStack stack) {
        return stack.contains(ModDataComponents.KYBER_COLOR);
    }

    public static boolean isActivated(ItemStack stack) {
        Boolean activated = stack.get(ModDataComponents.ACTIVATED);
        return activated != null && activated;
    }

    public static void setActivated(ItemStack stack, boolean activated) {
        stack.set(ModDataComponents.ACTIVATED, activated);
    }

    @Override
    public TypedActionResult<ItemStack> use(
            World world,
            PlayerEntity user,
            Hand hand
    ) {
        ItemStack saberStack = user.getStackInHand(hand);

        Hand otherHand = hand == Hand.MAIN_HAND
                ? Hand.OFF_HAND
                : Hand.MAIN_HAND;

        ItemStack otherStack = user.getStackInHand(otherHand);

        KyberColor newColor = getColorFromCrystal(otherStack);

        // Si un cristal coloré est dans l'autre main,
        // on remplace le cristal du sabre.
        if (newColor != null) {

            if (!world.isClient) {
                KyberColor currentColor = getKyberColor(saberStack);

                if (currentColor != newColor) {
                    setKyberColor(saberStack, newColor);

                    if (!user.getAbilities().creativeMode) {
                        otherStack.decrement(1);
                    }
                }
            }

            return TypedActionResult.success(
                    saberStack,
                    world.isClient()
            );
        }

        // Sinon : clic droit normal,
        // on allume ou on éteint le sabre.
        if (!world.isClient) {
            boolean activated = isActivated(saberStack);
            boolean newState = !activated;

            setActivated(saberStack, newState);

            if (newState) {
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        ModSounds.LIGHT_SABER_ON,
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F
                );
            } else {
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        ModSounds.LIGHT_SABER_OFF,
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }

        return TypedActionResult.success(
                saberStack,
                world.isClient()
        );
    }

    @Override
    public boolean postHit(
            ItemStack stack,
            LivingEntity target,
            LivingEntity attacker
    ) {
        if (!isActivated(stack)) {
            return super.postHit(stack, target, attacker);
        }

        if (!attacker.getWorld().isClient) {

            attacker.getWorld().playSound(
                    null,
                    target.getX(),
                    target.getBodyY(0.5),
                    target.getZ(),
                    ModSounds.LIGHT_SABER_HIT,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            if (attacker.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        target.getX(),
                        target.getBodyY(0.5),
                        target.getZ(),
                        12,
                        0.25,
                        0.35,
                        0.25,
                        0.05
                );
            }
            target.setOnFireFor(2.0F);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public float getBonusAttackDamage(
            Entity target,
            float baseAttackDamage,
            DamageSource damageSource
    ) {
        Entity attacker = damageSource.getAttacker();

        if (!(attacker instanceof PlayerEntity player)) {
            return 0.0F;
        }

        ItemStack stack = player.getMainHandStack();

        if (!stack.isOf(this)) {
            return 0.0F;
        }

        if (!isActivated(stack)) {
            return 0.0F;
        }

        return 7.0F;
    }

    private static KyberColor getColorFromCrystal(ItemStack stack) {

        if (stack.isOf(ModItems.WHITE_KYBER_CRYSTAL)) {
            return KyberColor.WHITE;
        }

        if (stack.isOf(ModItems.ORANGE_KYBER_CRYSTAL)) {
            return KyberColor.ORANGE;
        }

        if (stack.isOf(ModItems.MAGENTA_KYBER_CRYSTAL)) {
            return KyberColor.MAGENTA;
        }

        if (stack.isOf(ModItems.LIGHT_BLUE_KYBER_CRYSTAL)) {
            return KyberColor.LIGHT_BLUE;
        }

        if (stack.isOf(ModItems.YELLOW_KYBER_CRYSTAL)) {
            return KyberColor.YELLOW;
        }

        if (stack.isOf(ModItems.LIME_KYBER_CRYSTAL)) {
            return KyberColor.LIME;
        }

        if (stack.isOf(ModItems.PINK_KYBER_CRYSTAL)) {
            return KyberColor.PINK;
        }

        if (stack.isOf(ModItems.GRAY_KYBER_CRYSTAL)) {
            return KyberColor.GRAY;
        }

        if (stack.isOf(ModItems.LIGHT_GRAY_KYBER_CRYSTAL)) {
            return KyberColor.LIGHT_GRAY;
        }

        if (stack.isOf(ModItems.CYAN_KYBER_CRYSTAL)) {
            return KyberColor.CYAN;
        }

        if (stack.isOf(ModItems.PURPLE_KYBER_CRYSTAL)) {
            return KyberColor.PURPLE;
        }

        if (stack.isOf(ModItems.BLUE_KYBER_CRYSTAL)) {
            return KyberColor.BLUE;
        }

        if (stack.isOf(ModItems.BROWN_KYBER_CRYSTAL)) {
            return KyberColor.BROWN;
        }

        if (stack.isOf(ModItems.GREEN_KYBER_CRYSTAL)) {
            return KyberColor.GREEN;
        }

        if (stack.isOf(ModItems.RED_KYBER_CRYSTAL)) {
            return KyberColor.RED;
        }

        if (stack.isOf(ModItems.BLACK_KYBER_CRYSTAL)) {
            return KyberColor.BLACK;
        }

        return null;
    }
}