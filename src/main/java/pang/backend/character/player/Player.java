package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.Movement;
import pang.backend.config.GameConfig;
import pang.hardware.Screen;

import java.awt.*;

public class Player extends Character implements Movement {

    private int startPosX;
    private int startPosY;


    public Player(GameConfig config){
        super(config);
        addStat(config, "ammunition", "gravityForce", "posX", "posY");
        setPlayerStartPosition();
    }

    public void steer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
//        if(!(playerReaction == "PosY") && getStat("posY") <= 0 )
        stats.computeIfPresent(playerParameter, (k, v) -> v + value);
    }

    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);
        Double posX = stats.get("posX");
        Double posY = stats.get("posY");
        System.out.println(posX);

        int dx = posX.intValue();
        int dy = posY.intValue();

        playerGraphic.fillRect(startPosX + dx, startPosY + dy, getPlayerWidth(), getPlayerHeight());
        //playerGraphic.fillRect((Screen.getPreferredGameWidth() + integerWidth)/2,Screen.getPreferredGameHeight()-2*integerHeight,  integerWidth, integerHeight);
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
    private int getPlayerWidth(){
        return stats.get("width").intValue();
    }

    void shoot(){
        //ammoAmount += -1;
        //Not implemented yet
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
