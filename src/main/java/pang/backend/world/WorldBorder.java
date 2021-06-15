package pang.backend.world;

import pang.backend.util.PangVector;
import pang.backend.character.Character;

import java.awt.geom.RectangularShape;

/**
 *
 */
public class WorldBorder {
    /**
     * lewa granica mapy
     */
    private final int leftBorder;
    /**
     * prawa granica mapy
     */
    private final int rightBorder;
    /**
     * górna granica mapy
     */
    private final int topBorder;
    /**
     * dolna granica mapy
     */
    private final int bottomBorder;

    /**
     * tworzy grance mapy na podstawie skrajnego punktu mapy
     * @param oppositeCorner skrajny punkt mapy
     */
    public WorldBorder(PangVector oppositeCorner) {
        this.rightBorder = oppositeCorner.getX();
        this.bottomBorder = oppositeCorner.getY();
        this.leftBorder = 0;
        this.topBorder = 0;
    }

    /**
     * sprawdza czy postać jest w granicach mapy
     * @param character sprawdzana postać
     * @param direction kierunek poruszania się
     * @param stepLength prędkość ruchu
     * @return wartość sprawdzenia czy postać jest w granicach mapy
     */
    public boolean isInBorderOfWorld(Character character, String direction, int stepLength) {
        if (direction.equals("motionVectorX") || direction.equals("posX"))
            return !isBehindRightBorder(character, stepLength) && !isBehindLeftBorder(character, stepLength);

        if (direction.equals("motionVectorY") || direction.equals("posY"))
            return !isBehindTopBorder(character, stepLength) && !isBehindBottomBorder(character, stepLength);

        return true;
    }

    /**
     * sprawdzenie czy gracz jest za lewą granicą
     * @param character sprawdzana postać
     * @param stepLength prędkość ruchu
     * @return wartość sprawdzenia czy gracz jest za lewą granicą
     */
    private boolean isBehindLeftBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        return position.getX() + stepLength < leftBorder;
    }

    /**
     * sprawdzenie czy gracz jest za prawą granicą
     * @param character sprawdzana postać
     * @param stepLength prędkość ruchu
     * @return wartość sprawdzenia czy gracz jest za prawą granicą
     */
    private boolean isBehindRightBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        RectangularShape hitBox = character.getHitBox();
        double width = hitBox.getWidth();
        return position.getX() + stepLength + width > rightBorder;
    }

    /**
     * sprawdzenie czy gracz jest za górną granicą
     * @param character sprawdzana postać
     * @param stepLength prędkość ruchu
     * @return wartość sprawdzenia czy gracz jest za górną granicą
     */
    private boolean isBehindTopBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        return position.getY() + stepLength < topBorder;
    }

    /**
     * sprawdzenie czy gracz jest za dolną granicą
     * @param character sprawdzana postać
     * @param stepLength prędkość ruchu
     * @return wartość sprawdzenia czy gracz jest za dolną granicą
     */
    private boolean isBehindBottomBorder(Character character, int stepLength) {
        PangVector position = character.getPosition();
        RectangularShape hitBox = character.getHitBox();
        double height = hitBox.getHeight();
        return position.getY() + stepLength + height > bottomBorder;
    }

}
