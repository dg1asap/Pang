package pang.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pang.backend.exceptions.ConfigNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {
    static EnemyFactory enemyFactory;

    @BeforeAll
    static void createEnemyFactory() throws ConfigNotFoundException {
        enemyFactory = new EnemyFactory();
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
}
