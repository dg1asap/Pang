package pang.backend.bullet;

import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

public class BulletCreator implements ResizeObserver {
    private PangVector size;

    public BulletCreator(PangVector size) {
        initialResize(size);
    }

    public Bullet create(int xPosition, int yPosition) {
        return new Bullet(xPosition, yPosition, size);
    }

    @Override
    public void initialResize(PangVector size) {
        this.size = size;
    }

    @Override
    public void resize(PangVector size) {
        this.size = size;
    }


}
