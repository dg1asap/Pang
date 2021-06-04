package pang.backend.world;

import pang.backend.util.PangVector;
import pang.backend.character.Character;

import java.awt.geom.RectangularShape;

public class WorldBorder {
    private final int leftBorder;
    private final int rightBorder;
    private final int topBorder;
    private final int bottomBorder;

    public WorldBorder(PangVector oppositeCorner) {
        this.rightBorder = oppositeCorner.getX();
        this.bottomBorder = oppositeCorner.getY();
        this.leftBorder = 0;
        this.topBorder = 0;
    }

    public boolean isInBorderOfWorld(Character character, String direction, int stepLength) {
        if ( direction.equals("posX"))
            return !isBehindRightBorder(character, stepLength) && !isBehindLeftBorder(character, stepLength);
        else
            return !isBehindTopBorder(character, stepLength) && !isBehindBottomBorder(character, stepLength);
    }

    private boolean isBehindLeftBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        return position.getX() + stepLength < leftBorder;
    }

    private boolean isBehindRightBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        RectangularShape hitBox = character.getHitBox();
        double width = hitBox.getWidth();
        return position.getX() + stepLength + width > rightBorder;
    }

    private boolean isBehindTopBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        return position.getY() + stepLength < topBorder;
    }

    private boolean isBehindBottomBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        RectangularShape hitBox = character.getHitBox();
        double height = hitBox.getHeight();
        return position.getY() + stepLength + height > bottomBorder;
    }

}
