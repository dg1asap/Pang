package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.Movement;
import pang.backend.config.GameConfig;

import java.awt.*;

public class Player extends Character implements Movement {
    public Player(GameConfig config){
        super(config);
        addStat(config, "ammunition", "gravityForce", "posX", "posY");
    }

    public void steer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
//        if(!(playerReaction == "PosY") && getStat("posY") <= 0 )
        stats.computeIfPresent(playerParameter, (k, v) -> v + value);
    }

    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);
        Double width = stats.get("width");
        Double height = stats.get("height");
        Double posX = stats.get("posX");
        Double posY = stats.get("posY");
        System.out.println(posX);

        int integerWidth = width.intValue();
        int integerHeight = height.intValue();
        int integerPosX = posX.intValue();
        int integerPosY = posY.intValue();
        playerGraphic.fillRect(integerPosX, integerPosY, integerWidth, integerHeight);
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
