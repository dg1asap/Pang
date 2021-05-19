package pang.backend.bullet;

import pang.backend.character.HitBox;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Bullet implements HitBox {
    private double xPosition;
    private double yPosition;

    public Bullet(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public RectangularShape getHitBox() {
        double width = 10;
        double height = 20;
        return new Rectangle2D.Double(xPosition, yPosition, width, height);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect((int)xPosition,(int)yPosition,10,20);
    }

    public void fire(){
        yPosition = yPosition - 1;
    }

    public int getY(){
        return (int)yPosition;
    }
}

