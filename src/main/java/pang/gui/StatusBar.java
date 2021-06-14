package pang.gui;

import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

import java.awt.*;

public class StatusBar implements ResizeObserver {
    private int score;
    private int health;
    private int ammo;
    private PangVector position;

    public StatusBar(int score, int health, int ammo){
        setNewPlayerInfo(score, health, ammo);
    }

    public void setNewPlayerInfo(int score, int health, int ammo){
        setScoreAndHealth(score, health);
        setAmmo(ammo);
    }

    public void draw (Graphics g){
        setFontPreferences(g);
        setContentAndPositionOfBar(g);
    }

    private void setScoreAndHealth(int score, int health) {
        this.score = score;
        this.health = health;
    }

    private void setAmmo(int ammo) {
        if(ammo > 0){
            ammo = ammo - 1;
        }
        this.ammo = ammo;
    }

    private void setFontPreferences(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.PLAIN,15));
    }

    private void setContentAndPositionOfBar(Graphics g) {
        int width = position.getX() - g.getFontMetrics().stringWidth("Score: " + score);
        int height = 20;
        int verticalSpacingBetweenStats = 15;
        int horizontalSpacingBetweenFrame = -30;
        g.drawString("Score: " + score, width + horizontalSpacingBetweenFrame, height);
        g.drawString("Health: " + health, width + horizontalSpacingBetweenFrame, height + verticalSpacingBetweenStats);
        g.drawString("Ammo: " + ammo, 5, 20);
    }

    @Override
    public void initialResize(PangVector size) {
        this.position = size;
    }

    @Override
    public void resize(PangVector size) {
        this.position = size;
    }
}
