package pang.backend.character.enemy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pang.backend.exception.ConfigException;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {
    static EnemyFactory enemyFactory;

    @BeforeAll
    static void createEnemyFactory() {
        Path configPath = Path.of("./data/test/configs/EnemyFactoryTest.txt");
        enemyFactory = EnemyFactory.fromConfigPath(configPath);
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
