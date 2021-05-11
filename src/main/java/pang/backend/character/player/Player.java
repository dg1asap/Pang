package pang.backend.character.player;

import pang.backend.Bullet;
import pang.backend.BulletController;
import pang.backend.character.Character;
import pang.backend.config.GameConfig;
import pang.gui.InfoInGame;
import pang.gui.PangFrame;

import java.awt.*;

public class Player extends Character{

    private int startPosX;
    private int startPosY;
    private boolean isShooting = true;
    private InfoInGame infoInGame;

    public Player(GameConfig config){
        super(config);
        addStat(config, "ammunition", "gravityForce", "posX", "posY");
        setPlayerStartPosition();
        turnOffShooting();
        infoInGame = new InfoInGame(0,getStat("health").intValue(),getAmmoAmount());
    }

    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);
        int dx = getStat("posX").intValue();
        int dy = getStat("posY").intValue();

        playerGraphic.fillRect(startPosX + dx, startPosY + dy, getPlayerWidth(), getPlayerHeight());
        infoInGame.draw(playerGraphic);
    }

    public void steer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
        increaseStatByValue(playerParameter, value);
        shoot(keyChar);
        infoInGame.setNewPlayerInfo(1,getStat("health").intValue(),getAmmoAmount());
    }

    public int getActualYPlayerPosition(){
        return startPosY + getStat("posY").intValue();
    }

    private void setPlayerStartPosition(){
        startPosX = PangFrame.getPreferredGameWidth()/2 - getPlayerWidth()/2;
        startPosY = PangFrame.getPreferredGameHeight() - getPlayerHeight() - 42; //TODO player jest za nisko na wejściu nie wiem skąd te 42 przesunięcia
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

    public int getBulletXPos(){
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
    }

    private boolean getShootingStatus(){
        return isShooting;
    }

    public boolean canShoot() {
        if (getAmmoAmount() > 0 && getShootingStatus())
            return true;
        else return false;
    }
}
