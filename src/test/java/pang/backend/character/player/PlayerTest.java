package pang.backend.character.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pang.backend.config.ConfigLoader;
import pang.backend.config.GameConfig;
import pang.backend.exception.ConfigNotFoundException;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    static ConfigLoader configLoader;
    static GameConfig config;

    @BeforeAll
    static void setConfigLoader() throws ConfigNotFoundException {
        Path path = Path.of("./data/test/configs/PlayerTest.txt");
        configLoader = ConfigLoader.fromConfigPath(path);
        config = configLoader.getConfig("Player");
    }

    Player player = new Player(config);

    @Test
    void testPlayerHasStartHp(){
        assertTrue(player.isAlive());
    }

    @Test
    void testPlayerHasStartingAmmo(){
        assertTrue(player.getAmmoAmount()>0);
    }

    @Test
    void testPlayerCanGiveDamage(){
        assertTrue(player.getDamage()>0);
    }

    @Test
    void testPlayerHasGravitationSet(){
        assertTrue(player.getGravityForce()>0);
    }

    @Test
    void testPlayerIs2D(){
        assertTrue(arePlayerDimensionsLoaded());
    }

    private boolean arePlayerDimensionsLoaded(){
        return hasPlayerHeight() && hasPlayerWidth();
    }

    private boolean hasPlayerHeight(){
        return player.getHeight() > 0;
    }

    private boolean hasPlayerWidth(){
        return player.getWidth() > 0;
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
        return isXPositionDifferent(startX, endX) && isYPositionDifferent(startY, endY) && isSpeedSet();
    }

    private boolean isXPositionDifferent(double startX, double endX){
        return startX != endX;
    }

    private boolean isYPositionDifferent(double startY, double endY){
        return startY != endY;
    }

    private boolean isSpeedSet(){
        return player.getSpeed()>0;
    }

    @Disabled
    void testPlayerCollisionsAreWorking(){

       // double startX = player.getXPosition();
       // player.changeHorizontal();
       // player.changeHorizontal();

    }
}
