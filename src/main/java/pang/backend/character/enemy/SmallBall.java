package pang.backend.character.enemy;

import pang.backend.character.PangPosition;
import pang.backend.config.GameConfig;
import pang.backend.world.WorldBorder;
import pang.gui.PangFrame;

import java.awt.*;

public class SmallBall extends Enemy {
    private final PangPosition vectorMovement;

    public static SmallBall fromConfigAndSpawnTime(GameConfig config, int spawnTime){
        return new SmallBall(config, spawnTime);
    }

    protected SmallBall(GameConfig config, int spawnTime){
        super(config, spawnTime);
        vectorMovement = PangPosition.randPangVector(-10, 10);
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
