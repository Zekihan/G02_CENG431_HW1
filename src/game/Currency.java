package game;

public enum Currency {

    COIN (100, false),
    MAGNETIC_COIN (200, true),
    DIAMOND (400, false);

    private final boolean magnetic;
    private final int value;

    Currency(int value, boolean magnetic){
        this.value = value;
        this.magnetic = magnetic;
    }

    public boolean isMagnetic() {
        return magnetic;
    }

    public int getValue() {
        return value;
    }
}
