package game;

public enum Move {

    JUMP(0.05, 2), SLIDE(0.05, 2);

    private final double failChance;
    private final int point;

    Move(double failChance, int point) {
        this.failChance = failChance;
        this.point = point;
    }

    public double getFailChance() {
        return failChance;
    }

    public int getPoint() {
        return point;
    }
}
