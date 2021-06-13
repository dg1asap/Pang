package pang.backend.bullet;

import pang.backend.util.PangVector;

public class BulletCreator {
    private PangVector size;

    public BulletCreator(PangVector size) {
        this.size = size;
    }

    public Bullet create(int xPosition, int yPosition) {
        return new Bullet(xPosition, yPosition, size);
    }

}
