package game;

import java.util.HashMap;
import java.util.Map;

public class Hero {

    private Map<Collectable,Integer> chest;
    private boolean magnet;
    private int position;

    public Hero(){
        this(new HashMap<>(), false, 1);
    }

    public Hero(Map<Collectable, Integer> chest, boolean magnet, int position) {
        setChest(chest);
        this.magnet = magnet;
        this.position = position;
    }

    public Hero(Hero hero){
        setChest(hero.getChest());
        this.magnet = hero.hasMagnet();
        setPosition(hero.getPosition());
    }

    public void collect(Collectable collectable){
        int oldCount = chest.getOrDefault(collectable, 0);
        chest.put(collectable, oldCount + 1);
    }

    public boolean hasMagnet(){
        return magnet;
    }

    public void acquireMagnet(){
        magnet = true;
    }

    public int totalItems(){
        int total = 0;
        for(Collectable key: chest.keySet()){
            total += chest.get(key);
        }
        return total;
    }

    public int totalDiamonds(){
        return chest.getOrDefault(Collectable.DIAMOND, 0);
    }

    public int getPosition() {
        return position;
    }

    public Map<Collectable, Integer> getChest() {
        Map<Collectable, Integer> copyChest = new HashMap<>(chest.size());
        for(Collectable nextCollectable : chest.keySet()){
            copyChest.put(nextCollectable, chest.get(nextCollectable));
        }
        return copyChest;
    }

    private void setChest(Map<Collectable, Integer> chest){
        if(chest == null){
            throw new IllegalArgumentException("Given chest object is null therefore cannot be set.");
        }
        this.chest = chest;
    }

    public void setPosition(int position){
        if(position < 0){
            throw new IllegalArgumentException("Given position argument cannot be a negative value.");
        }
        this.position = position;
    }
}
