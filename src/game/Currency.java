package game;

//TODO: Convert it into a class maybe?
public enum Currency {

    COIN (100, false),
    MAGNETIC_COIN (200, true),
    DIAMOND (400, false);

    private final boolean requiresMagnet;
    private final int value;

    private Currency(int value, boolean requiresMagnet){
        this.value = value;
        this.requiresMagnet = requiresMagnet;
    }

    public boolean requiresMagnet() {
        return requiresMagnet;
    }

    public int getValue() {
        return value;
    }
}
