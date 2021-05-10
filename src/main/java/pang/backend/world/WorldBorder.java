package pang.backend.world;

import pang.backend.character.PangPosition;
import pang.backend.character.Character;

public class WorldBorder {
    private int leftBorder;
    private int rightBorder;
    private int topBorder;
    private int bottomBorder;

    public WorldBorder(PangPosition oppositeCorner) {
        this.rightBorder = oppositeCorner.getX();
        this.bottomBorder = oppositeCorner.getY();
        this.leftBorder = 0;
        this.topBorder = 0;
    }

    public boolean isInBorderOfWorld(Character character, String direction, int stepLength) {
        PangPosition characterPosition = character.getPosition();
        if ( direction.equals("posX"))
            return !isBehindRightBorder(characterPosition, stepLength) && !isBehindLeftBorder(characterPosition, stepLength);
        else
            return !isBehindTopBorder(characterPosition, stepLength) && !isBehindBottomBorder(characterPosition, stepLength);
    }

    private boolean isBehindLeftBorder(PangPosition position, int stepLength) {
        return position.getX() + stepLength < leftBorder;
    }

    private boolean isBehindRightBorder(PangPosition position, int stepLength) {
        return position.getX() + stepLength + 42 > rightBorder;
    }

    private boolean isBehindTopBorder(PangPosition position, int stepLength) {
        return position.getY() + stepLength < topBorder;
    }

    private boolean isBehindBottomBorder(PangPosition position, int stepLength) {
        return position.getY() + stepLength + 70 > bottomBorder;

    }

}
