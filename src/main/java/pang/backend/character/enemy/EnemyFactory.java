package pang.backend.character.enemy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.character.CoolDown;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.config.ConfigLoader;

public class EnemyFactory {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private GameConfig enemyConfig;
    private CoolDown enemyCoolDown;
    private String enemyName;
    private int spawnTime;

    public Enemy createEnemyWithNameAndRespawnTime(String name, Integer spawnTime) throws IllegalArgumentException {
        setEnemyNameAndSpawnTime(name, spawnTime);
        setEnemyConfig(name);
        setEnemyCoolDown(name);
        return tryGetEnemy();
    }

    private void setEnemyNameAndSpawnTime(String name, int spawnTime) {
        this.enemyName = name;
        this.spawnTime = spawnTime;
    }

    private void setEnemyConfig(String name) {
        enemyConfig = ConfigLoader.CONFIG_LOADER.getConfig(name);
    }

    private void setEnemyCoolDown(String name) {
        GameConfig coolDownConfig = ConfigLoader.CONFIG_LOADER.getConfig(name + "CoolDown");
        this.enemyCoolDown = new CoolDown(coolDownConfig);
    }

    private Enemy tryGetEnemy() {
        System.out.println("#" + enemyName + "#");
        try {
            return getEnemy();
        } catch (IllegalArgumentException e) {
            logger.error("[EnemyLoader] Enemy named " + enemyName + " was not found");
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