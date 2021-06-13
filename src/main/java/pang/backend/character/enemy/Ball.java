package pang.backend.character.enemy;

import pang.backend.character.CoolDown;
import pang.backend.util.PangVector;
import pang.backend.properties.config.GameConfig;
import pang.backend.world.WorldBorder;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

public abstract class Ball extends Enemy{
    private final PangVector vectorMovement;

    public Ball(GameConfig config, CoolDown coolDown, int spawnTime) {
        super(config, coolDown, spawnTime);
        vectorMovement = PangVector.randPangVector(-10, 10);
    }

    @Override
    public void moveInsideBorder(WorldBorder border) {
        if (!coolDown.isCoolDown("move")) {
            bounceOffVerticalWall(border);
            bounceOffHorizontalWall(border);
            changePosition();
        }
    }

    @Override
    public RectangularShape getHitBox() {
        double posX = getStat("posX");
        double posY = getStat("posY");
        double width = getStat("width");
        double height = getStat("height");
        return new Ellipse2D.Double(posX, posY, width, height);
    }

    public void bounceOff() {
        if (!coolDown.isCoolDown("bounceOff")) {
            vectorMovement.invertX();
            vectorMovement.invertY();
        }
    }

    private void bounceOffVerticalWall(WorldBorder wall) {
        if (!wall.isInBorderOfWorld(this, "posX", vectorMovement.getX()))
            vectorMovement.invertX();
    }

    private void bounceOffHorizontalWall(WorldBorder wall) {
        if (!wall.isInBorderOfWorld(this, "posY", vectorMovement.getY()))
            vectorMovement.invertY();
    }

    private void changePosition() {
        double intVectorX = vectorMovement.getX();
        double intVectorY = vectorMovement.getY();
        increaseStatByValue("posX", intVectorX);
        increaseStatByValue("posY", intVectorY);
    }


}
