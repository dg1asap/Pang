package pang.backend.character.enemy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.character.CoolDown;
import pang.backend.config.GameConfig;
import pang.backend.config.ConfigLoader;

import java.nio.file.Path;
import java.util.ArrayList;

public class EnemyFactory {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final ConfigLoader configLoader;
    private final ArrayList <GameConfig> enemiesConfigs = new ArrayList<>();
    private GameConfig enemyConfig;
    private CoolDown enemyCoolDown;
    private String enemyName;
    private int spawnTime;

    public static EnemyFactory fromConfigPath(Path configPath) {
        return new EnemyFactory(configPath);
    }

    public Enemy createEnemyWithNameAndRespawnTime(String name, Integer spawnTime) throws IllegalArgumentException {
        setEnemyNameAndSpawnTime(name, spawnTime);
        setEnemyConfig(name);
        setEnemyCoolDown(name);
        return tryGetEnemy();
    }

    protected EnemyFactory(Path configPath) {
        configLoader = ConfigLoader.fromConfigPath(configPath);
    }

    private void setEnemyNameAndSpawnTime(String name, int spawnTime) {
        this.enemyName = name;
        this.spawnTime = spawnTime;
    }

    private void setEnemyConfig(String name) {
        setEnemyConfigFromData(name);
        ifNoEnemyConfigInDataSetNew(name);
    }

    private void setEnemyConfigFromData(String name) {
        enemiesConfigs.forEach((config) -> ifEnemyConfigHasNameSet(config, name));
    }

    private void ifEnemyConfigHasNameSet(GameConfig config, String name) {
        if(config.hasName(name))
            enemyConfig = config;
    }

    private void ifNoEnemyConfigInDataSetNew(String name) {
        if (!hasEnemyConfig(name))
            setNewEnemyConfig(name);
    }

    private boolean hasEnemyConfig(String name) {
        return enemyConfig != null && enemyConfig.hasName(name);
    }

    private void setNewEnemyConfig(String name) {
        enemyConfig = configLoader.getConfig(name);
        enemiesConfigs.add(enemyConfig);
    }

    private void setEnemyCoolDown(String name) {
        GameConfig coolDownConfig = configLoader.getConfig(name + "CoolDown");
        this.enemyCoolDown = new CoolDown(coolDownConfig);
    }

    private Enemy tryGetEnemy() {
        try {
            return getEnemy();
        } catch (IllegalArgumentException e) {
            logger.error("[EnemyLoader] Enemy named " + enemyName + "not found");
        }
        return null;
    }

    private Enemy getEnemy() throws IllegalArgumentException {
        return switch (enemyName) {
            case "SmallBall" -> new SmallBall(enemyConfig, enemyCoolDown, spawnTime);
            case "LargeBall" -> new LargeBall(enemyConfig, enemyCoolDown, spawnTime);
            case "MegaBall" -> new MegaBall(enemyConfig, enemyCoolDown, spawnTime);
            default -> throw new IllegalArgumentException();
        };
    }


}