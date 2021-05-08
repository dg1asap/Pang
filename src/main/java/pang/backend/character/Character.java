package pang.backend.character;

import pang.backend.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class Character {
    protected Map<String, Double> stats = new HashMap<>();

    //boolean collision = false;
    //CharacterPosition position = new CharacterPosition();

    public Character(GameConfig config){
        addStat(config,"health", "damage", "speed", "height", "width");
    }

    protected void addStat(GameConfig config, String... newStats) {
        for(String stat : newStats) {
            stats.put(stat, config.getAttribute(stat));
        }
    }

    public double getStat(String statName) {
        return stats.get(statName);
    }


/*
    public double getYPosition(){
        return position.getVertical();
    }

    public double getXPosition(){
        return position.getHorizontal();
    }

    public void setPosX(double posX){
        position.setHorizontal(posX);
    }

    public void setPosY(double posY){
        position.setVertical(posY);
    }

    public void changePosX(double dx){
        position.changeHorizontal(dx);
    }

    public void changePosY(double dy){
        position.changeVertical(dy);
    }
 */


    public boolean isAlive(){
        return stats.get("health") > 0;
    }

}
