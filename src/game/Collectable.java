package game;


public enum Collectable {

    COIN (100, false),
    MAGNETIC_COIN (200, true),
    DIAMOND (400, false);

    private final boolean requiresMagnet;
    private final int value;

    Collectable(int value, boolean requiresMagnet){
        this.value = value;
        this.requiresMagnet = requiresMagnet;
    }

    public boolean requiresMagnet() {
        return requiresMagnet;
    }

    public int getValue() {
        return value;
    }

    public String toString() {

        String name = this.name();
        name = name.toLowerCase();
        name = name.replace('_', ' ');

        return "a " + name + " (" + this.getValue() + "pts)"; }
}
