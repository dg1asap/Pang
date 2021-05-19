package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.CoolDown;
import pang.backend.config.GameConfig;
import pang.gui.InfoInGame;
import pang.gui.frame.PangFrame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Player extends Character{

    private boolean isShooting = false;
    private boolean isJumping = false;
    private final InfoInGame infoInGame;

    public Player(GameConfig config, CoolDown coolDown) {
        super(config, coolDown);
        addStat(config, "ammunition", "gravityForce");
        setPlayerStartPosition();
        turnOffShooting();
        infoInGame = new InfoInGame(0,getStat("health").intValue(),getAmmoAmount());
    }

    @Override
    public RectangularShape getHitBox() {
        double posX = getStat("posX");
        double posY = getStat("posY");
        double width = getStat("width");
        double height = getStat("height");
        return new Rectangle2D.Double(posX, posY, width, height);
    }

    @Override
    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);
        int dx = getStat("posX").intValue();
        int dy = getStat("posY").intValue();

        playerGraphic.fillRect(dx, dy, getPlayerWidth(), getPlayerHeight());
        infoInGame.draw(playerGraphic);
    }

    public void steerKey(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
        jump(keyChar);
        shoot(keyChar);
        increaseStatByValue(playerParameter, value);

    }

    public void setNewPlayerInfo(){
        infoInGame.setNewPlayerInfo(1,getStat("health").intValue(),getAmmoAmount());
    }

    public int getActualYPlayerPosition(){
        return getStat("posY").intValue();
    }

    private void setPlayerStartPosition(){
        int startPosX = PangFrame.getActualScreenWidth() / 2 - getPlayerWidth() / 2;
        int startPosY = PangFrame.getActualScreenHeight() - getPlayerHeight() - 50; //TODO player jest za nisko na wejściu nie wiem skąd te 42 przesunięcia
        increaseStatByValue("posX", startPosX);
        increaseStatByValue("posY", startPosY);
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
        return getPlayerWidth()/2 + getStat("posX").intValue() - 5;
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

    public boolean canPlayerJump(){
        //return true;
        return !isJumping;
    }

    public boolean canShoot() {
        return getAmmoAmount() > 0 && getShootingStatus();
    }

    private void jump(char keyChar){
        if(keyChar =='w'){
            isJumping = true;
        }
    }

    public void gravity(){
        if(getActualYPlayerPosition()<PangFrame.getActualScreenHeight()-getPlayerHeight()){
            increaseStatByValue("posY", getStat("gravityForce").intValue());
        }
        else if(getActualYPlayerPosition()>=PangFrame.getActualScreenHeight()-getPlayerHeight() - 100){
            isJumping = false;
        }
    }

}
