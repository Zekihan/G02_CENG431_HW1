package game;

public enum Move {
    JUMP(Probability.STUMBLE.getChance(), 2), SLIDE(Probability.STUMBLE.getChance(), 2);

    private final double probability;
    private final int point;

    Move(double probability, int point) {
        this.probability = probability;
        this.point = point;
    }

    public double getProbability() {
        return probability;
    }

    public int getPoint() {
        return point;
    }
}
