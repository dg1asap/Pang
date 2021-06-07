package pang.backend.bullet;

import pang.backend.character.Character;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BulletController {
    private final ConcurrentLinkedQueue <Bullet> bullets = new ConcurrentLinkedQueue<>();
    private final Character owner;

    public BulletController(Character owner){
        this.owner = owner;
    }

    public void draw(Graphics g){
        for (Bullet bullet : bullets)
            bullet.draw(g);
    }

    public void steer(){
        for (Bullet bullet : bullets) {
            if(bullet.getY()<0){
                removeBullet(bullet);
            }
            bullet.fire();
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void interact(Character target) {
        for (Bullet bullet : bullets)
            if (bullet.intersects(target))
                owner.attack(target);
    }

    public void rescaleBullets() {
        for (Bullet bullet : bullets)
            bullet.rescale();
    }


}
