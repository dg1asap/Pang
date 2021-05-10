package pang.backend.character;

import pang.backend.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class Character {
    private Map<String, Double> stats = new HashMap<>();

    public Character(GameConfig config){
        addStat(config,"health", "damage", "speed", "height", "width", "posX", "posY");
    }

    protected void addStat(GameConfig config, String... newStats) {
        for(String stat : newStats) {
            stats.put(stat, config.getAttribute(stat));
        }
    }

    protected Double getStat(String statName) {
        return stats.get(statName);
    }

    protected void increaseStatByValue(String stat, double value){
        stats.computeIfPresent(stat, (k, v) -> v + value);
    }

    public PangPosition getPosition() {
        int intPosX = getStat("posX").intValue();
        int intPosY = getStat("posY").intValue();
        return new PangPosition(intPosX, intPosY);
    }

    public boolean isAlive(){
        return stats.get("health") > 0;
    }

}
