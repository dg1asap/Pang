package pang.backend.character;

import java.awt.geom.RectangularShape;

public interface HitBox {
    default boolean intersects(HitBox hitbox) {
        RectangularShape myHitbox = this.getHitBox();
        RectangularShape overlappingHitbox = hitbox.getHitBox();

        double posX = myHitbox.getX();
        double posY = myHitbox.getY();
        double width = myHitbox.getWidth();
        double height = myHitbox.getHeight();

        return overlappingHitbox.intersects(posX, posY, width, height);
    }

    RectangularShape getHitBox();

}
