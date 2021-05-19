package pang.backend.world;

import pang.backend.util.PangVector;
import pang.backend.character.Character;

public class WorldBorder {
    private int leftBorder;
    private int rightBorder;
    private int topBorder;
    private int bottomBorder;

    public WorldBorder(PangVector oppositeCorner) {
        this.rightBorder = oppositeCorner.getX();
        this.bottomBorder = oppositeCorner.getY();
        this.leftBorder = 0;
        this.topBorder = 0;
    }

    public boolean isInBorderOfWorld(Character character, String direction, int stepLength) {
        PangVector characterPosition = character.getPosition();
        if ( direction.equals("posX"))
            return !isBehindRightBorder(characterPosition, stepLength) && !isBehindLeftBorder(characterPosition, stepLength);
        else
            return !isBehindTopBorder(characterPosition, stepLength) && !isBehindBottomBorder(characterPosition, stepLength);
    }

    private boolean isBehindLeftBorder(PangVector position, int stepLength) {
        return position.getX() + stepLength < leftBorder;
    }

    private boolean isBehindRightBorder(PangVector position, int stepLength) {
        return position.getX() + stepLength + 42 > rightBorder;
    }

    private boolean isBehindTopBorder(PangVector position, int stepLength) {
        return position.getY() + stepLength < topBorder;
    }

    private boolean isBehindBottomBorder(PangVector position, int stepLength) {
        return position.getY() + stepLength  > bottomBorder;

    }

}
