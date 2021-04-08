package pang.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldAndWorldLoaderTest {
    static World world1;
    static World world2;

    @BeforeAll
    static void loadEnemiesFromTxtFile(){
        world1 = WorldLoader.loadLevel(999999999);
        world2 = WorldLoader.loadLevel(999999998);
    }

    @Test
    void testWorldIsClear(){
        assertFalse( world1.isEmpty() );
        assertFalse( world2.isEmpty() );
    }

    @Test
    void testEnemiesInQueue(){
        Enemy enemyFromWorld1 = world1.spawnFirstEnemy();
        Enemy enemyFromWorld2 = world2.spawnFirstEnemy();

        assertTrue(enemyFromWorld1 instanceof SmallBall);
        assertTrue(enemyFromWorld2 instanceof LargeBall);
    }

    @Test
    void testPlayerSpawn(){
        assertFalse( world1.isGameOver() );
        assertFalse( world2.isGameOver() );
    }
}
