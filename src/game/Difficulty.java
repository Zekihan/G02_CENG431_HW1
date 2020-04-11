package game;

public enum Difficulty {

    EASY(1),
    MODERATE(2),
    HARD(3),
    EXPERT(4);

    private final int value;

    Difficulty(int val){
        value = val;
    }

}
