package pang.backend;

import java.awt.*;

public class Bullet {
    private double xPosition;
    private double yPosition;

    public Bullet(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
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

