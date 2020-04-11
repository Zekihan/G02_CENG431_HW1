package game;

import java.util.HashMap;
import java.util.Map;

public class Hero {

    private Map<Currency,Integer> chest;
    private boolean magnet;

    public Hero() {
        this.chest = new HashMap<>();
        this.magnet = false;
    }

    public boolean hasMagnet(){
        return this.magnet;
    }

    public Map<Currency, Integer> getChest() {
        return chest;
    }


}
