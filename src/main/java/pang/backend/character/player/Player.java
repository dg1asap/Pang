package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.CoolDown;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.gui.InfoInGame;
import pang.gui.frame.PangFrame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Player extends Character implements Info {

    private boolean isShooting = false;
    private boolean isJumping = false;
    private final InfoInGame infoInGame;
    private GameInfo playerInfo;

    public Player(GameConfig config, CoolDown coolDown) {
        super(config, coolDown);
        addStat(config, "ammunition", "gravityForce");
        setPlayerStartPosition();
        turnOffShooting();
        this.infoInGame = new InfoInGame(0,getStat("health").intValue(),getAmmoAmount());
        this.playerInfo = new GameInfo("Player");
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

    @Override
    public GameInfo getGameInfo() {
        addScoreToGameInfo();
        return playerInfo;
    }

    public void steerKey(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
        jump(keyChar);
        shoot(keyChar);
        increaseStatByValue(playerParameter, value);

    }

    public void setNewPlayerInfo(){
        int score = this.getStat("score").intValue();
        int health = this.getStat("health").intValue();
        infoInGame.setNewPlayerInfo(score, health, getAmmoAmount());
    }

    public int getActualYPlayerPosition(){
        return getStat("posY").intValue();
    }

    public int getBulletXPos(){
        return getPlayerWidth()/2 + getStat("posX").intValue() - 5;
    }

    public boolean canPlayerJump(){
        //return true;
        return !isJumping;
    }

    public boolean canShoot() {
        return getAmmoAmount() > 0 && getShootingStatus();
    }

    public void gravity(){
        if(getActualYPlayerPosition()<PangFrame.getActualScreenHeight()-getPlayerHeight()){
            increaseStatByValue("posY", getStat("gravityForce").intValue());
        }
        else if(getActualYPlayerPosition()>=PangFrame.getActualScreenHeight()-getPlayerHeight() - 50){
            isJumping = false;
        }
    }

    private void addScoreToGameInfo() {
        Double score = getStat("score");
        GameConfig gameConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        Double k = gameConfig.getAttribute("difficulty");
        Double p = getStat("health");
        Double s = getStat("ammunition");

        Double scoreWithBonuses = score + Math.ceil( 10 * ( 0.028*Math.pow(k,6) - 0.4*Math.pow(k,2) + 2.3*k - 2.55 + 0.4*(p-2.5) + 0.8*(s-3) ) + 29);
        playerInfo.addAttribute("scoreWithBonus", scoreWithBonuses.toString());
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

    private void shoot(char keyChar){
        turnOffShooting();
        turnOnShootingIfKeyPressed(keyChar);
    }

    private void turnOffShooting(){
        isShooting = false;
    }

    private void turnOnShootingIfKeyPressed(char keyChar){
        if(keyChar =='k' || keyChar =='K'){
            isShooting = true;
        }
    }

    private boolean getShootingStatus(){
        return isShooting;
    }


    private void jump(char keyChar){
        if(keyChar =='w'){
            isJumping = true;
        }
    }


}