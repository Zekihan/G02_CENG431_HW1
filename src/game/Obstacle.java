package game;

public abstract class Obstacle implements IAvoidable{

    private final Move necessaryMove;

    public Obstacle(Move necessaryMove) {
        this.necessaryMove = necessaryMove;
    }

    public Move getNecessaryMove() {
        return necessaryMove;
    }

    public double getAvoidChance(){
        return 1-necessaryMove.getChanceOfFail();
    }

    public int getAvoidPoint(){
        return necessaryMove.getPoint();
    }

}


