package pang.backend.character.enemy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pang.backend.properties.config.ConfigLoader;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {
    static EnemyFactory enemyFactory;

    @BeforeAll
    static void createEnemyFactory() {
        Path configPath = Path.of("./data/test/configs/EnemyFactoryTest.txt");
        ConfigLoader.CONFIG_LOADER.init(configPath);
        enemyFactory = new EnemyFactory();
    }

    @Test
    void testSpawnSmallBall() {
        Enemy smallBall = enemyFactory.createEnemyWithNameAndRespawnTime("SmallBall", 9999999);
        assertTrue(smallBall instanceof SmallBall);
    }

    @Test
    void testSpawnLargeBall() {
        Enemy largeBall = enemyFactory.createEnemyWithNameAndRespawnTime("LargeBall", 1000);
        assertTrue(largeBall instanceof LargeBall);
    }

    @Test
    void testMegaLargeBall() {
        Enemy megaBall = enemyFactory.createEnemyWithNameAndRespawnTime("MegaBall", 1000);
        assertTrue(megaBall instanceof MegaBall);
    }
}
