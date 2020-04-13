package game;

import java.util.HashMap;
import java.util.Map;

public class Hero {

    private Map<Currency,Integer> chest;
    private boolean magnet;
    private int position;

    public Hero(){
        this(new HashMap<>(), false, 0);
    }

    public Hero(Map<Currency, Integer> chest, boolean magnet, int position) {
        this.chest = chest;
        this.magnet = magnet;
        this.position = position;
    }

    public void putToChest(Currency currency){
        int oldCount = chest.getOrDefault(currency, 0);
        chest.put(currency, oldCount + 1);
    }

    public boolean hasMagnet(){
        return this.magnet;
    }

    public void giveMagnet(){
        magnet = true;
    }

    public int totalItems(){
        int total = 0;
        for(Currency key: chest.keySet()){
            total += chest.get(key);
        }
        return total;
    }

    public int totalDiamonds(){
        return chest.getOrDefault(Currency.DIAMOND, 0);
    }

    public void incrementPosition(){
        position++;
    }

    public void resetPosition(){
        position = 0;
    }

    public int getPosition() {
        return position;
    }

}
