package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.Movement;
import pang.backend.config.GameConfig;

public class Player extends Character implements Movement {

    int ammoAmount;
    double gravityForce;
    double dx = 1;
    double dy = 1;

    public Player(GameConfig config){

        super(config);
        ammoAmount = (int) config.getAttribute("ammunition");
        gravityForce = config.getAttribute("gravityForce");

        this.setPosX(config.getAttribute("startPosX"));
        this.setPosY(config.getAttribute("startPosY"));
    }

    void shoot(){
        ammoAmount += -1;
        //Not implemented yet
    }

    int getAmmoAmount(){
        return ammoAmount;
    }

    double getGravityForce(){
        return gravityForce;
    }


    @Override
    public void changeYDirection(){
        this.changePosY(dy);
    }

    @Override
    public void changeXDirection(){
        this.changePosX(dx);
    }

    @Override
    public boolean isCollision() {
        return false; //Not implemented yet
    }

    //public boolean wallCollisionHappened(){
    //    if(this.getXPosition() == 0 || this.getXPosition() == GameWindow.getXSize()){ //window size ex. (0,1000);
    //        return true;
    //   }
    //}

   // public boolean enemyCollisionHappened(){

   // }

}