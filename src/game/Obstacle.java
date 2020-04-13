package game;

public enum Obstacle {
    ROCK(Move.JUMP),
    SAW(Move.JUMP),
    FELLED_TREE(Move.SLIDE),
    AQUEDUCT(Move.SLIDE);

    private final Move necessaryMove;

    Obstacle(Move necessaryMove) {
        this.necessaryMove = necessaryMove;
    }

    public Move getNecessaryMove() {
        return necessaryMove;
    }

    public double getChance(){
        return necessaryMove.getProbability();
    }

    public int getPoint(){
        return necessaryMove.getPoint();
    }



}


