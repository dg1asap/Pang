package pang.backend.character.enemy;

import pang.backend.character.HitBox;
import pang.backend.character.PangVector;
import pang.backend.config.GameConfig;
import pang.backend.world.WorldBorder;
import pang.gui.PangFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

public class LargeBall extends Enemy {
    private final PangVector vectorMovement;

    public static LargeBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new LargeBall(config, spawnTime);
    }

    protected LargeBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
        vectorMovement = PangVector.randPangVector(-10, 10);
        spawnEnemyAtTopOfMap();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(getStat("posX").intValue(), getStat("posY").intValue(), getStat("width").intValue(), getStat("height").intValue());
    }

    @Override
    public void move() {
        WorldBorder wall = new WorldBorder(PangFrame.getExtremePointOfFrame());
        bounceOffVerticalWall(wall);
        bounceOffHorizontalWall(wall);
        changePosition();
    }

    @Override
    public boolean intersects(HitBox hitbox) {
        return false;
    }

    @Override
    public RectangularShape getHitBox() {
        double posX = getStat("posX");
        double posY = getStat("posY");
        double width = getStat("width");
        double height = getStat("height");
        return new Ellipse2D.Double(posX, posY, width, height);
    }

    private void spawnEnemyAtTopOfMap() {
        int posX = PangVector.randComponentOfVector(50,PangFrame.getActualScreenWidth() - 50);
        int posY = 50;
        Double actualPosX = getStat("posX");
        Double actualPosY = getStat("posY");

        increaseStatByValue("posX", posX - actualPosX);
        increaseStatByValue("posY", posY - actualPosY);
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

