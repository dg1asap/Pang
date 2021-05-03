package pang.frontend;

import java.awt.*;

public class Bullet extends Rectangle {
    private double xPos;
    private double yPos;

    Bullet(double xPos, double yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void fly(){
        xPos -= 10;
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(2,2,3 ,10);
    }
}
