package pang.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    GameConfig config = new GameConfig();
    Player player = new Player(config);
    CharacterPosition position;

    @Test
    void testPlayerHasStartingAmmo(){
        assertTrue(player.getAmmoAmount()>0);
    }

    @Test
    void testPlayerCanMove(){
        double firstYPosition = position.getVertical();
        double firstXPosition = position.getHorizontal();

        player.changeHorizontal();
        player.changeVertical();

        assertTrue(isCharacterPositionDifferent(firstXPosition,firstYPosition,player.getXPosition(), player.getYPosition()));
    }

    private boolean isCharacterPositionDifferent(double startX, double startY, double endX, double endY){
        return isXPositionDifferent(startX, endX) && isYPositionDifferent(startY, endY);
    }

    private boolean isXPositionDifferent(double startX, double endX){
        return startX != endX;
    }

    private boolean isYPositionDifferent(double startY, double endY){
        return startY != endY;
    }

    @Test
    void testPlayerHasStartHp(){
        assertTrue(player.isAlive());
    }

    @Disabled
    void testPlayerCollisionsAreWorking(){

       // double startX = player.getXPosition();
       // player.changeHorizontal();
       // player.changeHorizontal();

    }
}
