package game;

public enum Level {

    EASY(1),
    MODERATE(2),
    HARD(3),
    EXPERT(4);

    private final int multiplier;

    Level(int val){
        multiplier = val;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
