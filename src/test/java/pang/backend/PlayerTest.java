package pang.backend;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    Player player = new Player();
    SmallBall smallBall = new SmallBall();

    PositionOfPlayer playerPos = new PositionOfPlayer();
    PositionOfPlayer newPlayerPos = new PositionOfPlayer();
    GamePlayWindow gameWindow = new GamePlayWindow();

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


    @Test
    void testPlayerCollisions(){
        playerPos.changeVertical();
        assertTrue(player.isCollision() > 0 ); //kolizja z lewą stroną ekranu

        playerPos.changeVertical();
        assertTrue(player.isCollision() <= gameWindow.maxSize()); //kolizja z prawą stroną ekranu
    }
}
