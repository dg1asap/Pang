package pang.backend.character.player;

import pang.backend.Bullet;
import pang.backend.BulletController;
import pang.backend.character.Character;
import pang.backend.character.Movement;
import pang.backend.config.GameConfig;
import pang.hardware.Screen;

import java.awt.*;

public class Player extends Character implements Movement {

    private int startPosX;
    private int startPosY;
    private BulletController bulletController;
    private boolean isShooting = true;

    private void setIsShooting(){
        isShooting = true;
    }
    private void setIsNotShooting(){
        isShooting = false;
    }

    public void loadBullets(){
        this.bulletController = new BulletController();
    }

    public Player(GameConfig config){
        super(config);
        addStat(config, "ammunition", "gravityForce", "posX", "posY");
        setPlayerStartPosition();
        loadBullets();
        setIsNotShooting();
    }

    public int getStartPosX(){
        return startPosX;
    }
    public int getStartPosY(){
        return startPosY;
    }

    public int getX(){
        return startPosX + stats.get("posX").intValue();
    }

    public int getY(){
        return startPosY + stats.get("posY").intValue();
    }

    public int getAmmoAmount(){
        return (int)stats.get("ammunition").intValue();
    }

    public void steer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
//        if(!(playerReaction == "PosY") && getStat("posY") <= 0 )
        stats.computeIfPresent(playerParameter, (k, v) -> v + value);

        //strzelanie
        shoot(keyChar);

    }

    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);
        Double posX = stats.get("posX");
        Double posY = stats.get("posY");
        System.out.println(posX);
        Double ammo = stats.get("ammunition");
        System.out.println("xxxxxxzxxxxxxxxxxxxxxxxx");
        System.out.println(ammo);

        int dx = posX.intValue();
        int dy = posY.intValue();

        playerGraphic.fillRect(startPosX + dx, startPosY + dy, getPlayerWidth(), getPlayerHeight());


        if(stats.get("ammunition").intValue()>0 && isShooting){
            bulletController.addBullet(new Bullet(getBulletXPos(),getStartPosY()+dy - 20));
            bulletController.draw(playerGraphic);
        }

        //playerGraphic.fillRect((Screen.getPreferredGameWidth() + integerWidth)/2,Screen.getPreferredGameHeight()-2*integerHeight,  integerWidth, integerHeight);
    }

    private int getBulletXPos(){
         return getStartPosX() + getPlayerWidth()/2 + stats.get("posX").intValue() - 5;
    }

    public void checkWallCollisions(){

    }

    private void setPlayerStartPosition(){
        startPosX = Screen.getPreferredGameWidth()/2 - getPlayerWidth()/2;
        startPosY = Screen.getPreferredGameHeight() - getPlayerHeight() - 42; //TODO player jest za nisko na wejściu nie wiem skąd te 42 przesunięcia
        System.out.println(startPosY);
        System.out.println(Screen.getPreferredGameHeight());
        System.out.println(getPlayerHeight());
    }

    private int getPlayerHeight(){
        return stats.get("height").intValue();
    }
    public int getPlayerWidth(){
        return stats.get("width").intValue();
    }


    private void shoot(char keyChar){
        setIsNotShooting();

        if(keyChar =='k'){
            setIsShooting();
        }
        if(stats.get("ammunition").intValue()>0 && isShooting){
            bulletController.tick();
        }
    }


    @Override
    public void changeYDirection(){
        //this.changePosY(dy);
    }

    @Override
    public void changeXDirection(){
        //this.changePosX(dx);
    }

    @Override
    public boolean isCollision() {
        return false; //Not implemented yet
    }


}
