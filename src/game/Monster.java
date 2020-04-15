package game;

public class Monster {

    private final double eatChance;
    private final String eatResult;

    public Monster(){
        this(0.02, "Eaten by a monster");
    }

    public Monster(double eatChance, String eatMessage) {
        this.eatChance = eatChance;
        this.eatResult = eatMessage;
    }

    public double getEatChance() {
        return eatChance;
    }

    public String getEatResult() {
        return eatResult;
    }

    private void setEatChance(int eatChance){
        if(eatChance <= 0){
            throw new IllegalArgumentException("Given eat chance argument must be greater than 0.");
        }
    }
}
