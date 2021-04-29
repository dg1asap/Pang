package pang.backend.world;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pang.backend.character.enemy.Enemy;
import pang.backend.character.enemy.LargeBall;
import pang.backend.character.enemy.SmallBall;
import pang.backend.exception.ConfigException;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class WorldAndWorldLoaderTest {
    static WorldLoader worldLoader1;
    static WorldLoader worldLoader2;
    static World world1;
    static World world2;

    @BeforeAll
    static void loadEnemiesFromTxtFile() throws ConfigException {
        Path configPath = Path.of("./data/test/configs/WorldAndWorldLoaderTest.txt");
        Path pathToLevel1 = Path.of("./data/test/level/999999999.txt");
        Path pathToLevel2 = Path.of("./data/test/level/999999998.txt");
        worldLoader1 = WorldLoader.fromConfigPathAndLevelPath(configPath, pathToLevel1);
        worldLoader2 = WorldLoader.fromConfigPathAndLevelPath(configPath, pathToLevel2);
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
        Enemy enemyFromWorld1 = world1.spawnEnemy();
        Enemy enemyFromWorld2 = world2.spawnEnemy();

        assertTrue(enemyFromWorld1 instanceof SmallBall);
        assertTrue(enemyFromWorld2 instanceof LargeBall);
    }

    @Test
    void testPlayerSpawn(){
        assertFalse( world1.isGameOver() );
        assertFalse( world2.isGameOver() );
    }
}