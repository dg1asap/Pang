package pang.backend.character.enemy;

import pang.backend.character.Character;
import pang.backend.config.GameConfig;

import java.awt.*;

public abstract class Enemy extends Character {
    private boolean spawned = false;
    protected int spawnTime;

    protected Enemy(GameConfig config, int spawnTime){
        super(config);
        this.spawnTime = spawnTime;
    }

    public void spawn(long time) {
        if (time > 1000)
            spawned = true;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public abstract void draw(Graphics g);

    public abstract void move();

    public abstract void takeDamage(double damage);

    public abstract double attack();

}
