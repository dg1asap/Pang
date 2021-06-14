package pang.backend.bullet;

import pang.backend.character.Character;
import pang.backend.util.PangVector;

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
            ifBulletLeavesMapRemove(bullet);
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
            consumeBulletOnTarget(bullet, target);
    }

    public void rescaleBullets(PangVector size) {
        for (Bullet bullet : bullets)
            bullet.resize(size);
    }

    private void ifBulletLeavesMapRemove(Bullet bullet) {
        if(bullet.getY()<0)
            removeBullet(bullet);
    }

    private void consumeBulletOnTarget(Bullet bullet, Character target) {
        if (bullet.intersects(target)) {
            owner.attack(target);
            bullets.remove(bullet);
        }
    }


}
