package pang.backend.character;

import java.awt.geom.RectangularShape;

public interface HitBox {
    default boolean intersects(HitBox hitbox) {
        RectangularShape myHitBox = this.getHitBox();
        RectangularShape overlappingHitBox = hitbox.getHitBox();

        double posX = myHitBox.getX();
        double posY = myHitBox.getY();
        double width = myHitBox.getWidth();
        double height = myHitBox.getHeight();

        return overlappingHitBox.intersects(posX, posY, width, height);
    }

    RectangularShape getHitBox();

}
