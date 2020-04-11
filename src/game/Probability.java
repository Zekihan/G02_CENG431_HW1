package game;

public enum Probability {

    STUMBLE(0.05),
    MONSTER(0.02);

    private final double chance;

    Probability(double chance) {
        this.chance = chance;
    }

    public double getChance() {
        return chance;
    }
}
