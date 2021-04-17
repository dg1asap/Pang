package pang.backend.character.enemy;

import pang.backend.config.GameConfig;
import pang.backend.config.ConfigLoader;
import pang.backend.exception.ConfigNotFoundException;

import java.nio.file.Path;

public class EnemyFactory {
    GameConfig smallBallConfig;
    GameConfig largeBallConfig;
    GameConfig megaBallConfig;

    public static EnemyFactory fromConfigPath(Path configPath) throws ConfigNotFoundException{
        return new EnemyFactory(configPath);
    }

    public Enemy create(String name, Integer spawnTime) throws IllegalArgumentException{
        return switch (name) {
            case "smallBall" -> new SmallBall(smallBallConfig, spawnTime);
            case "largeBall" -> new LargeBall(largeBallConfig, spawnTime);
            case "megaBall" -> new MegaBall(largeBallConfig, spawnTime);
            default -> throw new IllegalArgumentException("Enemy name not found");
        };
    }

    protected EnemyFactory(Path configPath) throws ConfigNotFoundException {
        ConfigLoader smallBallConfigLoader = ConfigLoader.fromConfigPath(configPath);
        ConfigLoader largeBallConfigLoader = ConfigLoader.fromConfigPath(configPath);
        ConfigLoader megaBallConfigLoader = ConfigLoader.fromConfigPath(configPath);

        smallBallConfig = smallBallConfigLoader.getConfig("SmallBall");
        largeBallConfig = largeBallConfigLoader.getConfig("LargeBall");
        megaBallConfig = megaBallConfigLoader.getConfig("MegaBall");
    }

}