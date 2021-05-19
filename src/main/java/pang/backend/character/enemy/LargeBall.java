package pang.backend.character.enemy;

import pang.backend.character.CoolDown;
import pang.backend.util.PangVector;
import pang.backend.config.GameConfig;
import pang.gui.frame.PangFrame;

import java.awt.*;

public class LargeBall extends Ball{
    protected LargeBall(GameConfig config, CoolDown coolDown, int spawnTime){
        super(config, coolDown, spawnTime);
        spawnEnemyAtTopOfMap();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(getStat("posX").intValue(), getStat("posY").intValue(), getStat("width").intValue(), getStat("height").intValue());
    }

    private void spawnEnemyAtTopOfMap() {
        int posX = PangVector.randComponentOfVector(50,PangFrame.getActualScreenWidth() - 50);
        int posY = 50;
        Double actualPosX = getStat("posX");
        Double actualPosY = getStat("posY");

        increaseStatByValue("posX", posX - actualPosX);
        increaseStatByValue("posY", posY - actualPosY);
    }

}

