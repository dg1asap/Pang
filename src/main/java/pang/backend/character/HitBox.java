package pang.backend.character;

import java.awt.geom.RectangularShape;

/**
 * Interfejs reprezentujący hitbox, czyli obwódkę obiektu wchodzącą w interakcje z innym obiektami implementującymi ten interfejs
 */
public interface HitBox {
    /**
     * sprawdza czy hitboxy przecinają się
     * @param hitbox porównywany hitbox
     * @return wynik sprawdzenia czy hitboxy przecinają się
     */
    default boolean intersects(HitBox hitbox) {
        RectangularShape myHitBox = this.getHitBox();
        RectangularShape overlappingHitBox = hitbox.getHitBox();

        double posX = myHitBox.getX();
        double posY = myHitBox.getY();
        double width = myHitBox.getWidth();
        double height = myHitBox.getHeight();

        return overlappingHitBox.intersects(posX, posY, width, height);
    }

    /**
     * zwraca hitbox obietku
     * @return hitbox
     */
    RectangularShape getHitBox();

}
