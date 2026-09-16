package dev.raenmel.yodaspath.item;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.component.ModDataComponents;
import dev.raenmel.yodaspath.item.custom.BlasterItem;
import dev.raenmel.yodaspath.item.custom.LightSaberItem;
import dev.raenmel.yodaspath.item.kyber.KyberColor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item KYBER_CRYSTAL =
            registerItem("kyber_crystal");

    public static final Item WHITE_KYBER_CRYSTAL =
            registerItem("white_kyber_crystal");

    public static final Item ORANGE_KYBER_CRYSTAL =
            registerItem("orange_kyber_crystal");

    public static final Item MAGENTA_KYBER_CRYSTAL =
            registerItem("magenta_kyber_crystal");

    public static final Item LIGHT_BLUE_KYBER_CRYSTAL =
            registerItem("light_blue_kyber_crystal");

    public static final Item YELLOW_KYBER_CRYSTAL =
            registerItem("yellow_kyber_crystal");

    public static final Item LIME_KYBER_CRYSTAL =
            registerItem("lime_kyber_crystal");

    public static final Item PINK_KYBER_CRYSTAL =
            registerItem("pink_kyber_crystal");

    public static final Item GRAY_KYBER_CRYSTAL =
            registerItem("gray_kyber_crystal");

    public static final Item LIGHT_GRAY_KYBER_CRYSTAL =
            registerItem("light_gray_kyber_crystal");

    public static final Item CYAN_KYBER_CRYSTAL =
            registerItem("cyan_kyber_crystal");

    public static final Item PURPLE_KYBER_CRYSTAL =
            registerItem("purple_kyber_crystal");

    public static final Item BLUE_KYBER_CRYSTAL =
            registerItem("blue_kyber_crystal");

    public static final Item BROWN_KYBER_CRYSTAL =
            registerItem("brown_kyber_crystal");

    public static final Item GREEN_KYBER_CRYSTAL =
            registerItem("green_kyber_crystal");

    public static final Item RED_KYBER_CRYSTAL =
            registerItem("red_kyber_crystal");

    public static final Item BLACK_KYBER_CRYSTAL =
            registerItem("black_kyber_crystal");

    public static final Item BLASTER_BOLT =
            registerItem("blaster_bolt");

    public static final Item SCRAP_METAL =
            registerItem("scrap_metal");

    public static final Item BLASTER_CELL =
            Registry.register(
                    Registries.ITEM,
                    Identifier.of(YodaSPath.MOD_ID, "blaster_cell"),
                    new Item(
                            new Item.Settings()
                                    .maxCount(1)
                                    .maxDamage(30)
                    )
            );

    public static final Item LIGHT_SABER =
            Registry.register(
                    Registries.ITEM,
                    Identifier.of(YodaSPath.MOD_ID, "light_saber"),
                    new LightSaberItem(
                            new Item.Settings()
                                    .maxCount(1)
                                    .component(
                                            ModDataComponents.KYBER_COLOR,
                                            KyberColor.WHITE.getId()
                                    )
                                    .component(
                                            ModDataComponents.ACTIVATED,
                                            false
                                    )
                    )
            );

    private static Item registerItem(String name) {
        Identifier id = Identifier.of(YodaSPath.MOD_ID, name);

        return Registry.register(
                Registries.ITEM,
                id,
                new Item(new Item.Settings())
        );
    }
    public static final Item BLASTER =
            Registry.register(
                    Registries.ITEM,
                    Identifier.of(YodaSPath.MOD_ID, "blaster"),
                    new BlasterItem(
                            new Item.Settings()
                                    .maxCount(1)
                    )
            );

    public static void initialize() {
        YodaSPath.LOGGER.info("Registering Yoda's Path items");
    }
}