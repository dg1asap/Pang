package pang.backend;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    Player player = new Player();
    SmallBall smallBall = new SmallBall();

    PositionOfPlayer playerPos = new PositionOfPlayer();
    PositionOfPlayer newPlayerPos = new PositionOfPlayer();

    @Test
    void testPlayerCanShoot(){
        player.shoot();
        assertTrue(smallBall.takeDamage()>0);
    }

    @Test
    void testPlayerHasStartingAmmo(){
        assertTrue(player.getAmmo()>0);
    }

    @Test
    void testPlayerCanMove(){
        assertTrue(newPlayerPos.changeVertical() != playerPos.vertical);
        assertTrue(newPlayerPos.changeHorizontal() != playerPos.horizontal);
    }

}
