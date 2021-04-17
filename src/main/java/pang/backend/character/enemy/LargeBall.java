package pang.backend.character.enemy;

import pang.backend.config.GameConfig;

public class LargeBall extends Enemy {
    public static LargeBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new LargeBall(config, spawnTime);
    }

    protected LargeBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
    }
}

