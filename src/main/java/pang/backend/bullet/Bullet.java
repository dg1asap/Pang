package pang.backend.bullet;

import pang.backend.character.HitBox;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Bullet implements HitBox, ResizeObserver {
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;

    public Bullet(double xPosition, double yPosition, PangVector size){
        GameConfig bulletConfig = ConfigLoader.CONFIG_LOADER.getConfig("Bullet");
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = bulletConfig.getAttribute("width");
        this.height = bulletConfig.getAttribute("height");
        width = size.getScaledToInitialXof(width);
        height = size.getScaledToInitialYof(height);
    }

    @Override
    public RectangularShape getHitBox() {
        return new Rectangle2D.Double(xPosition, yPosition, width, height);
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


    @Override
    public void initialResize(PangVector size) {
        //width = size.getScaledToInitialXof(width);
        //height = size.getScaledToInitialYof(height);
    }

    @Override
    public void resize(PangVector size) {
        xPosition = size.getScaledXof(xPosition);
        yPosition = size.getScaledYof(yPosition);
        width = size.getScaledXof(width);
        height = size.getScaledYof(height);
    }
}
