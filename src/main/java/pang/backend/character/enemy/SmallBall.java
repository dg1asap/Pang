package pang.backend.character.enemy;

import pang.backend.config.GameConfig;

public class SmallBall extends Enemy {
    public static SmallBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new SmallBall(config, spawnTime);
    }

    protected SmallBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
    }
}
