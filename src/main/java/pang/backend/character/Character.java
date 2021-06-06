package pang.backend.character;

import pang.backend.properties.config.GameConfig;
import pang.backend.util.PangVector;
import pang.gui.frame.PangFrame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Character implements HitBox {
    protected CoolDown coolDown;
    private final Map<String, Double> stats = new HashMap<>();

    public Character(GameConfig config, CoolDown coolDown){
        addStat(config,"health", "damage", "speed", "height", "width", "posX", "posY", "score");
        this.coolDown = coolDown;
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

    protected void scaleStatToX(String statName) {
        PangVector extremePointOfFrame = PangFrame.getExtremePointOfFrame();
        double stat = getStat(statName);
        double scaledStat = extremePointOfFrame.getScaledXof(stat);
        stats.replace(statName, scaledStat);
    }

    protected void scaleStatToY(String statName) {
        PangVector extremePointOfFrame = PangFrame.getExtremePointOfFrame();
        double stat = getStat(statName);
        double scaledStat = extremePointOfFrame.getScaledYof(stat);
        stats.replace(statName, scaledStat);
    }

    public PangVector getPosition() {
        int intPosX = getStat("posX").intValue();
        int intPosY = getStat("posY").intValue();
        return new PangVector(intPosX, intPosY);
    }

    public boolean isDead(){
        return stats.get("health") < 0;
    }

    public void attack(Character character) {
        if (characterCanAttack()) {
            damageCharacter(character);
            stealPoints(character);
        }
    }

    public abstract void draw(Graphics g);

    private boolean characterCanAttack() {
        return !coolDown.isCoolDown("attack");
    }

    private void damageCharacter(Character target) {
        double damage = this.getStat("damage");
        target.increaseStatByValue("health", -damage);
    }

    private void stealPoints(Character target) {
        if (target.isDead()) {
            Double score = target.getStat("score");
            this.increaseStatByValue("score", score);
        }
    }

}
