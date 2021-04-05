package pang.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WorldAndWorldLoaderTest {
    static World world1;
    static World world2;

    @BeforeAll
    static void loadEnemiesFromTxtFile(){
        world1 = WorldLoader.loadLevel(1);
        world2 = WorldLoader.loadLevel(2);
    }

    @Test
    void testWorldIsClear(){
        Assertions.assertFalse( world1.isEmpty() );
        Assertions.assertFalse( world2.isEmpty() );
    }

    @Test
    void testEnemysInQueue(){
        SmallBall smallBall = new SmallBall();
        LargeBall largeBall = new LargeBall();

        Assertions.assertSame( smallBall, world1.spawnFirstEnemy() );
        Assertions.assertSame( largeBall, world2.spawnFirstEnemy() );
    }

    @Test
    void testPlayerSpawn(){
        Assertions.assertFalse( world1.isGameOver() );
        Assertions.assertFalse( world2.isGameOver() );
    }
}
