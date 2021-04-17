package pang.backend.character.enemy;

import pang.backend.config.GameConfig;

public class MegaBall extends Enemy {
    public static MegaBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new MegaBall(config, spawnTime);
    }

    protected MegaBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
    }
}
