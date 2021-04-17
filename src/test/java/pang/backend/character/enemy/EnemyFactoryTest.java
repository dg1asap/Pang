package pang.backend.character.enemy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pang.backend.exception.ConfigNotFoundException;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {
    static EnemyFactory enemyFactory;

    @BeforeAll
    static void createEnemyFactory() throws ConfigNotFoundException {
        Path configPath = Path.of("./data/test/configs/EnemyFactoryTest.txt");
        enemyFactory = EnemyFactory.fromConfigPath(configPath);
    }

    @Test
    void testSpawnSmallBall(){
        Enemy smallBall = enemyFactory.create("smallBall", 9999999);
        assertTrue(smallBall instanceof SmallBall);
    }

    @Test
    void testSpawnLargeBall(){
        Enemy largeBall = enemyFactory.create("largeBall", 1000);
        assertTrue(largeBall instanceof LargeBall);
    }

    @Test
    void testMegaLargeBall(){
        Enemy megaBall = enemyFactory.create("megaBall", 1000);
        assertTrue(megaBall instanceof MegaBall);
    }
}
