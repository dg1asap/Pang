package pang.backend.character.enemy;

import pang.backend.character.PangVector;
import pang.backend.config.GameConfig;
import pang.backend.world.WorldBorder;
import pang.gui.PangFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

public class SmallBall extends Enemy {
    private final PangVector vectorMovement;
    private Ellipse2D.Double shape;

    public static SmallBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new SmallBall(config, spawnTime);
    }

    protected SmallBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
        vectorMovement = PangVector.randPangVector(-10, 10);
        spawnEnemyAtTopOfMap();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(getStat("posX").intValue(), getStat("posY").intValue(), getStat("width").intValue(), getStat("height").intValue());
    }

    @Override
    public void move() {
        WorldBorder wall = new WorldBorder(PangFrame.getExtremePointOfFrame());
        bounceOffVerticalWall(wall);
        bounceOffHorizontalWall(wall);
        changePosition();
    }

    @Override
    public void takeDamage(double damage) {
        increaseStatByValue("health", -1);
    }

    @Override
    public double attack() {
        return 0;
    }

    @Override
    public RectangularShape getHitBox() {
        double posX = getStat("posX");
        double posY = getStat("posX");
        double width = getStat("width");
        double height = getStat("height");
        return new Ellipse2D.Double(posX, posY, width, height);
    }

    private void spawnEnemyAtTopOfMap() {
        int posX = PangVector.randComponentOfVector(50,PangFrame.getActualScreenWidth() - 50);
        int posY = 50;
        double actualPosX = getStat("posX");
        double actualPosY = getStat("posY");

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
