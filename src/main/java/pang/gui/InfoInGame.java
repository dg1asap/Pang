package pang.gui;

import pang.hardware.Screen;

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
        this.ammo = ammo;
    }


    public void draw (Graphics g){
        g.setColor(Color.black);

        g.setFont(new Font("Consolas", Font.PLAIN,15));
        g.drawString("Score: " + score, PangFrame.getActualScreenWidth() - g.getFontMetrics().stringWidth("Score: " + score) - 10, 20);
        g.drawString("Health: " + health, PangFrame.getActualScreenWidth() - g.getFontMetrics().stringWidth("Score: " + health) - 10, 40);
        g.drawString("Ammo: " + ammo, 5, 20);
    }

}
