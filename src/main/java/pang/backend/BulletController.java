package pang.backend;

import pang.backend.character.player.Player;
import pang.backend.character.player.PlayerReaction;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class BulletController {
    private LinkedList<Bullet> bullets = new LinkedList<Bullet>();

    Bullet tempBullet;

    public BulletController(){
    }

    public void tick(){
        for(int i = 0; i<bullets.size(); i++){
            tempBullet = bullets.get(i);

            if(tempBullet.getY()<0){
                removeBullet(tempBullet);
            }
            tempBullet.tick();
        }
    }

    public void draw(Graphics g){
        for(int i = 0; i<bullets.size(); i++){
            tempBullet = bullets.get(i);
            tempBullet.draw(g);
        }
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet){
        bullets.remove(bullet);
    }



}
