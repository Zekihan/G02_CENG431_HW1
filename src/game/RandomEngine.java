package game;

import java.util.Random;

public class RandomEngine {

    public static int randPerimeterInRange(int start, int end){
        Random rand = new Random();
        return rand.nextInt(end - start + 1) + start;
    }

    public static TrackType randTrackType(){
        TrackType[] trackTypes = TrackType.values();
        Random rand = new Random();
        int i = rand.nextInt(trackTypes.length);
        return trackTypes[i];
    }

    public static Level randLevel(){
        Level[] difficulties = Level.values();
        Random rand = new Random();
        int i = rand.nextInt(difficulties.length);
        return difficulties[i];
    }

}
