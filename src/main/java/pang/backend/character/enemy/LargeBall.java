package pang.backend.character.enemy;

import pang.backend.config.GameConfig;

import java.awt.*;

public class LargeBall extends Enemy {
    public static LargeBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new LargeBall(config, spawnTime);
    }

    protected LargeBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void move() {

    }

    @Override
    public void takeDamage(double damage) {
        increaseStatByValue("health", 1);
    }

    @Override
    public double attack() {
        return 0;
    }
}

