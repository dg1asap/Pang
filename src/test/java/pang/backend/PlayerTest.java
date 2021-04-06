package pang.backend;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    Player player = new Player();

    @Test
    void testPlayerIsLoaded(){
        assertTrue(player.getHealth()>0);
        assertTrue(player.getSpeed()>0);
        assertTrue(player.getDamage()>0);
        assertTrue(player.isAlive() == true);
    }

    @Test
    void testPlayerIsShooting(){

    }

    @Test
    void testPlayerHasStartingAmmo(){

    }

    @Test
    void testPlayer(){

    }

}
