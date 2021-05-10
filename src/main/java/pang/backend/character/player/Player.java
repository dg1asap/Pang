package pang.backend.character.player;

import pang.backend.Bullet;
import pang.backend.BulletController;
import pang.backend.character.Character;
import pang.backend.config.GameConfig;
import pang.gui.InfoInGame;
import pang.hardware.Screen;

import java.awt.*;

public class Player extends Character{

    private int startPosX;
    private int startPosY;
    private BulletController bulletController;
    private boolean isShooting = true;
    private InfoInGame infoInGame;

    public Player(GameConfig config){
        super(config);
        addStat(config, "ammunition", "gravityForce", "posX", "posY");
        setPlayerStartPosition();
        loadBullets();
        turnOffShooting();
        infoInGame = new InfoInGame(0,getStat("health").intValue(),getAmmoAmount());
    }

    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);
        int dx = getStat("posX").intValue();
        int dy = getStat("posY").intValue();

        playerGraphic.fillRect(startPosX + dx, startPosY + dy, getPlayerWidth(), getPlayerHeight());
        if(getStat("ammunition").intValue()>0 && isShooting){
            bulletController.addBullet(new Bullet(getBulletXPos(),startPosY + dy - 20));
        }
        bulletController.draw(playerGraphic);
        infoInGame.draw(playerGraphic);
    }

    public void steer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
        increaseStatByValue(playerParameter, value);
        shoot(keyChar);
        infoInGame.setNewPlayerInfo(1,getStat("health").intValue(),getAmmoAmount());
    }

    private void setPlayerStartPosition(){
        startPosX = Screen.getPreferredGameWidth()/2 - getPlayerWidth()/2;
        startPosY = Screen.getPreferredGameHeight() - getPlayerHeight() - 42; //TODO player jest za nisko na wejściu nie wiem skąd te 42 przesunięcia
    }

    private void loadBullets(){
        this.bulletController = new BulletController();
    }

    private int getAmmoAmount(){
        if(getStat("ammunition")>0){
            return getStat("ammunition").intValue();
        }
        else return 0;
    }

    private int getPlayerHeight(){
        return getStat("height").intValue();
    }

    private int getPlayerWidth(){
        return getStat("width").intValue();
    }

    private int getBulletXPos(){
        return startPosX + getPlayerWidth()/2 + getStat("posX").intValue() - 5;
    }

    private void shoot(char keyChar){
        turnOffShooting();
        turnOnShootingIfKeyPressed(keyChar);
    }

    private void turnOffShooting(){
        isShooting = false;
    }

    private void turnOnShootingIfKeyPressed(char keyChar){
        if(keyChar =='k'){
            isShooting = true;
        }
        bulletController.fire();
    }
}
