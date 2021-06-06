package pang.backend.bullet;

import pang.backend.character.HitBox;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.util.PangObserver;
import pang.backend.util.PangVector;
import pang.gui.frame.PangFrame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Bullet implements HitBox, PangObserver {
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;

    public Bullet(double xPosition, double yPosition){
        GameConfig bulletConfig = ConfigLoader.CONFIG_LOADER.getConfig("Bullet");
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = bulletConfig.getAttribute("width");
        this.height = bulletConfig.getAttribute("height");
    }

    @Override
    public RectangularShape getHitBox() {
        return new Rectangle2D.Double(xPosition, yPosition, width, height);
    }

    @Override
    public void pangUpdate() {
        PangVector scalingVector = PangFrame.getExtremePointOfFrame();
        xPosition = scalingVector.getScaledXof(xPosition);
        yPosition = scalingVector.getScaledYof(yPosition);
        width = scalingVector.getScaledXof(width);
        height = scalingVector.getScaledYof(height);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect((int)xPosition,(int)yPosition,(int) width,(int) height);
    }

    public void fire(){
        yPosition = yPosition - 1;
    }

    public int getY(){
        return (int)yPosition;
    }


}
