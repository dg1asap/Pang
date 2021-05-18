package pang.backend.character.enemy;

import pang.backend.character.Character;
import pang.backend.character.CoolDown;
import pang.backend.config.GameConfig;

public abstract class Enemy extends Character {
    private boolean spawned = false;
    protected int spawnTime;

    protected Enemy(GameConfig config, CoolDown coolDown, int spawnTime){
        super(config, coolDown);
        this.spawnTime = spawnTime;
    }

    public void spawn(long time) {
        if (time > (long)spawnTime * 1000)
            spawned = true;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public abstract void move();

}
