package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.CharacterInfoFactory;
import pang.backend.character.CoolDown;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.backend.util.PangVector;
import pang.backend.world.WorldBorder;
import pang.gui.StatusBar;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Player extends Character implements Info {
    private boolean isShooting = false;
    private final StatusBar statusBar;

    public Player(GameConfig config, CoolDown coolDown) {
        super(config, coolDown);
        addStat(config, "motionVectorX", "motionVectorY", "motionVectorBlanking",
                                    "ammunition", "gravityForce", "gravityLimit");
        turnOffShooting();
        this.statusBar = new StatusBar(0,getStat("health").intValue(),getAmmoAmount());
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
        int width = getStat("width").intValue();
        int height = getStat("height").intValue();
        playerGraphic.fillRect(dx, dy, width, height);

        statusBar.draw(playerGraphic);
    }

    @Override
    public GameInfo getGameInfo() {
        CharacterInfoFactory infoFactory = new CharacterInfoFactory();
        return infoFactory.create(this);
    }

    public void steerKey(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
        shoot(keyChar);
        increaseStatByValue(playerParameter, value);
    }

    public void steerTime() {
        setNewPlayerInfo();
        move();
        affectByGravity();
    }

    public void limitMovement(WorldBorder border) {
        int motionVectorX = getStat("motionVectorX").intValue();
        int motionVectorY = getStat("motionVectorY").intValue();
        bounceOffRightAndLeftBorder(motionVectorX, border);
        blockGoAbroadX(motionVectorX, border);
        blockGoAbroadY(motionVectorY, border);
    }

    @Override
    public void initialResize(PangVector mapSize) {
        int frameWidth = mapSize.getX();
        int frameHeight = mapSize.getY();
        int startPosX = frameWidth / 2 - getStat("width").intValue() / 2;
        int startPosY = frameHeight - getStat("height").intValue();
        increaseStatByValue("posX", startPosX);
        increaseStatByValue("posY", startPosY);
        increaseStatByValue("gravityLimit", mapSize.getY());
        statusBar.initialResize(mapSize);
    }

    @Override
    public void resize(PangVector mapSize) {
        super.resize(mapSize);
        statusBar.resize(mapSize);
        double oldGravityLimit = getStat("gravityLimit");
        increaseStatByValue("gravityLimit", mapSize.getY() - oldGravityLimit);
    }

    public int getActualYPlayerPosition(){
        return getStat("posY").intValue();
    }

    public int getBulletXPos(){
        return getStat("width").intValue()/2 + getStat("posX").intValue() - 5;
    }

    public boolean canPlayerJump(){
        return !coolDown.isCoolDown("jumping");
    }

    public boolean canShoot() {
        return getAmmoAmount() > 0 && getShootingStatus();
    }

    public void useGravity(){
        if (isInGravityLimit())
            increaseStatByValue("posY", getStat("gravityForce"));
    }

    private void bounceOffRightAndLeftBorder(int motionVectorX, WorldBorder border) {
        if (!border.isInBorderOfWorld(this, "motionVectorX", motionVectorX))
            increaseStatByValue("motionVectorX", -2*motionVectorX);
    }

    private void blockGoAbroadX(int motionVectorX, WorldBorder border) {
        int posX = getStat("posX").intValue();
        if (!border.isInBorderOfWorld(this, "posX", motionVectorX)) {
            if (posX < 0) increaseStatByValue("posX", 1);
            if (posX > 0) increaseStatByValue("posX", -1);
        }
    }

    private void blockGoAbroadY(int motionVectorY, WorldBorder border) {
        int posY = getStat("posY").intValue();
        if (!border.isInBorderOfWorld(this, "posY", motionVectorY)) {
            if (posY < 1) increaseStatByValue("posY", 1);
            if (posY > 1) increaseStatByValue("posY", -1);
        }
    }

    private boolean isInGravityLimit() {
        double posY = getStat("posY");
        return posY < getStat("gravityLimit") - getStat("height");
    }

    private void move() {
        if (canMove())
            moveUsingMotionVectors();
    }

    private boolean canMove() {
        return !coolDown.isCoolDown("movingVector");
    }

    private void moveUsingMotionVectors() {
        double motionVectorX = getStat("motionVectorX");
        double motionVectorY = getStat("motionVectorY");
        double motionVectorBlanking = getStat("motionVectorBlanking");
        increaseStatByValue("motionVectorX", -motionVectorX / motionVectorBlanking);
        increaseStatByValue("motionVectorY", -motionVectorY / motionVectorBlanking);
        increaseStatByValue("posX", motionVectorX);
        increaseStatByValue("posY", motionVectorY);
    }

    private void affectByGravity() {
        if(canUseGravity())
            useGravity();
    }

    private boolean canUseGravity() {
        return !isGravityCoolDown();
    }

    private boolean isGravityCoolDown() {
        return coolDown.isCoolDown("gravity");
    }

    private void setNewPlayerInfo(){
        int score = this.getStat("score").intValue();
        int health = this.getStat("health").intValue();
        statusBar.setNewPlayerInfo(score, health, getAmmoAmount());
    }

    private int getAmmoAmount(){
        if(getStat("ammunition")>0){
            return getStat("ammunition").intValue();
        }
        else return 0;
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


}