package pang.gui;

import pang.backend.util.PangVector;
import pang.gui.frame.PangFrame;

import java.awt.*;

public class InfoInGame {

    private int score;
    private int health;
    private int ammo;

    public InfoInGame(int score, int health, int ammo){
        setNewPlayerInfo(score, health, ammo);
    }

    public void setNewPlayerInfo(int score, int health, int ammo){
        this.score = score;
        this.health = health;
        if(ammo > 0){
            ammo = ammo - 1;
        }
        this.ammo = ammo;

    }


    public void draw (Graphics g){
        g.setColor(Color.black);

        g.setFont(new Font("Consolas", Font.PLAIN,15));

        PangVector extremePointOfFrame =  PangFrame.getExtremePointOfFrame();
        int frameWidth = extremePointOfFrame.getX();
        g.drawString("Score: " + score, frameWidth - g.getFontMetrics().stringWidth("Score: " + score) - 10, 20);
        g.drawString("Health: " + health, frameWidth - g.getFontMetrics().stringWidth("Score: " + health) - 10, 40);

        g.drawString("Ammo: " + ammo, 5, 20);
    }

}
