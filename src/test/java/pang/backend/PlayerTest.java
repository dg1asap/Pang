package pang.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    Path path = Path.of("./data/test/player.txt");
    ConfigLoader configLoader = new ConfigLoader(path);
    GameConfig config = configLoader.getConfig("Player");

    Player player = new Player(config);

    @Test
    void testPlayerHasStartingAmmo(){
        assertTrue(player.getAmmoAmount()>0);
    }

    @Test
    void testPlayerCanMove(){
        double firstYPosition = player.getYPosition();
        double firstXPosition = player.getXPosition();

        player.changeXDirection();
        player.changeYDirection();

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
