package pang.backend.character.enemy;

import pang.backend.character.CoolDown;
import pang.backend.util.PangVector;
import pang.backend.properties.config.GameConfig;
import pang.gui.frame.PangFrame;

import java.awt.*;

public class SmallBall extends Ball {

    public SmallBall(GameConfig config, CoolDown coolDown, int spawnTime){
        super(config, coolDown, spawnTime);
        spawnEnemyAtTopOfMap();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(getStat("posX").intValue(), getStat("posY").intValue(), getStat("width").intValue(), getStat("height").intValue());
    }

    private void spawnEnemyAtTopOfMap() {
        PangVector extremePointOfFrame = PangFrame.getExtremePointOfFrame();
        int frameWidth = extremePointOfFrame.getX();
        int posX = PangVector.randComponentOfVector(50,frameWidth - 50);
        int posY = 50;
        double actualPosX = getStat("posX");
        double actualPosY = getStat("posY");

        increaseStatByValue("posX", posX - actualPosX);
        increaseStatByValue("posY", posY - actualPosY);
    }

}
