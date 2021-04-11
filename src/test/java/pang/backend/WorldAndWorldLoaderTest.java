package pang.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pang.backend.exceptions.ConfigNotFoundException;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class WorldAndWorldLoaderTest {
    static WorldLoader worldLoader1;
    static WorldLoader worldLoader2;
    static World world1;
    static World world2;

    @BeforeAll
    static void loadEnemiesFromTxtFile() throws ConfigNotFoundException {
        Path path1 = Path.of("./data/test/level/999999999.tx");
        Path path2 = Path.of("./data/test/level/999999998.tx");
        worldLoader1 = new WorldLoader(path1);
        worldLoader2 = new WorldLoader(path2);
        world1 = worldLoader1.getWorld();
        world2 = worldLoader2.getWorld();
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
