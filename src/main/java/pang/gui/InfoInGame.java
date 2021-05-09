package pang.gui;

import pang.hardware.Screen;

import java.awt.*;

public class InfoInGame {

    private int score;
    private int health;
    private int ammo;
    private int xPosOfInfo;

    public InfoInGame(Screen screen){
        setXPosOfInfo(screen);
    }

    public void setXPosOfInfo(Screen screen) {
        xPosOfInfo = screen.getPreferredGameWidth();
    }



    public void draw (Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.PLAIN,15));
        g.drawString("Score: " + score, xPosOfInfo - 90, 20);
        g.drawString("Health: " + health, xPosOfInfo - 90, 40);
        g.drawString("Ammo: " + ammo, 5, 20);
    }

}
