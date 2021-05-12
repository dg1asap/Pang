package pang.backend.character.enemy;

import pang.backend.config.GameConfig;

import java.awt.*;

public class MegaBall extends Enemy {
    public static MegaBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new MegaBall(config, spawnTime);
    }

    protected MegaBall(GameConfig config, int spawnTime){
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

    }

    @Override
    public double attack() {
        return 0;
    }
}
