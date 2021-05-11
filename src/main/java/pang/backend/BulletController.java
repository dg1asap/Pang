package pang.backend;

import java.awt.*;
import java.util.LinkedList;

public class BulletController {
    private LinkedList<Bullet> bullets = new LinkedList<Bullet>();

    private Bullet tempBullet;

    public BulletController(){
    }

    public void draw(Graphics g){
        for(int i = 0; i<bullets.size(); i++){
            tempBullet = bullets.get(i);
            tempBullet.draw(g);
        }
    }

    public void steer(){
        for(int i = 0; i<bullets.size(); i++){
            tempBullet = bullets.get(i);

            if(tempBullet.getY()<0){
                removeBullet(tempBullet);
            }
            tempBullet.fire();
        }
    }

    public void removeBullet(Bullet bullet){
        bullets.remove(bullet);
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }




}
