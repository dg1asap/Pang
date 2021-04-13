package pang.backend;

import pang.backend.exceptions.ConfigNotFoundException;

import java.nio.file.Path;

public class EnemyFactory {
    GameConfig smallBallConfig;
    GameConfig largeBallConfig;
    GameConfig megaBallConfig;

    EnemyFactory(Path configPath) throws ConfigNotFoundException {
        ConfigLoader smallBallConfigLoader = new ConfigLoader(configPath);
        ConfigLoader largeBallConfigLoader = new ConfigLoader(configPath);
        ConfigLoader megaBallConfigLoader = new ConfigLoader(configPath);

        smallBallConfig = smallBallConfigLoader.getConfig("SmallBall");
        largeBallConfig = largeBallConfigLoader.getConfig("LargeBall");
        megaBallConfig = megaBallConfigLoader.getConfig("MegaBall");
    }

    public Enemy create(String name, Integer spawnTime) throws IllegalArgumentException{
        return switch (name) {
            case "smallBall" -> new SmallBall(smallBallConfig, spawnTime);
            case "largeBall" -> new LargeBall(largeBallConfig, spawnTime);
            case "megaBall" -> new MegaBall(largeBallConfig, spawnTime);
            default -> throw new IllegalArgumentException("Enemy name not found");
        };
    }

}