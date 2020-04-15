package game;

public enum Move {
    //TODO: Seems to know about Probability enum. Maybe make a change here?
    JUMP(Probability.STUMBLE.getChance(), 2), SLIDE(Probability.STUMBLE.getChance(), 2);

    private final double chanceOfFail;
    private final int point;

    private Move(double chanceOfFail, int point) {
        this.chanceOfFail = chanceOfFail;
        this.point = point;
    }

    public double getChanceOfFail() {
        return chanceOfFail;
    }

    public int getPoint() {
        return point;
    }
}
