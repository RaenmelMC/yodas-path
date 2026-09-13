package dev.raenmel.yodaspath.item.kyber;

public enum KyberColor {

    WHITE("white"),
    ORANGE("orange"),
    MAGENTA("magenta"),
    LIGHT_BLUE("light_blue"),
    YELLOW("yellow"),
    LIME("lime"),
    PINK("pink"),
    GRAY("gray"),
    LIGHT_GRAY("light_gray"),
    CYAN("cyan"),
    PURPLE("purple"),
    BLUE("blue"),
    BROWN("brown"),
    GREEN("green"),
    RED("red"),
    BLACK("black");

    private final String id;

    KyberColor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static KyberColor fromId(String id) {
        for (KyberColor color : values()) {
            if (color.id.equals(id)) {
                return color;
            }
        }

        return null;
    }
}