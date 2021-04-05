package pang.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;

public class WorldAndWorldLoaderTest {
    World world1;
    World world2;

    @BeforeAll
    void loadEnemysFromTxtFile(){
        world1 = WorldLoader.loadLevel(1);
        world2 = WorldLoader.loadLevel(2);
    }

    @Test
    void testWorldIsClear(){
        Assertions.assertFalse( world1.isEmpty() );
        Assertions.assertFalse( world2.isEmpty() );
    }

    @Test
    @Disabled("Not implemented yet")
    void testEnemysInQueue(){
        SmallBall smallBall;
        LargeBall largeBall;

        Assertions.assertSame( smallBall, world1.spawnFirstEnemy() );
        Assertions.assertSame( largeBall, world2.spawnFirstEnemy() );
    }

    @Test
    void testPlayerSpawn(){
        Assertions.assertFalse( world1.isGameOver() );
        Assertions.assertFalse( world2.isGameOver() );
    }
}
