package pang.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemySpawnerTest {
    @Test
    void testSpawnSmallBall(){
        Enemy smallBall = EnemySpawner.spawn("smallBall", 9999999);
        assertTrue(smallBall instanceof SmallBall);
    }

    @Test
    void testSpawnLargeBall(){
        Enemy largeBall = EnemySpawner.spawn("largeBall", 1000);
        assertTrue(largeBall instanceof LargeBall);
    }
}
