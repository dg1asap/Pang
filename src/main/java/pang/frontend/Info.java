package pang.frontend;

import pang.backend.character.player.Player;

import java.awt.*;

public class Info {

    int score;
    int health;
    int ammo;

    Info(){

    }

    public void draw (Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.PLAIN,15));
        g.drawString("Score: " + String.valueOf(score), GamePanel.getGameWidth() - 90, 20);
        g.drawString("Health: " + String.valueOf(health), GamePanel.getGameWidth() - 90, 40);
        g.drawString("Ammo: " + String.valueOf(ammo), 5, 20);
    }
}
