package pang.backend;

public class Player extends Character implements Movement {

    double ammoAmount;
    double gravityForce;

    public Player(GameConfig config){
        this.health = config.getAttribute("health");
        this.damage = config.getAttribute("damage");
        this.speed = config.getAttribute("speed");

        ammoAmount = config.getAttribute("ammunition");
        gravityForce = config.getAttribute("gravityForce");

        this.position.setHorizontal(config.getAttribute("startPosX"));
        this.position.setVertical(config.getAttribute("startPosY"));
    }

    void shoot(){
        ammoAmount += -1;
    }

    double getAmmoAmount(){
        return ammoAmount;
    }

    double getYPosition(){
        return position.getVertical();
    }

    double getXPosition(){
        return position.getHorizontal();
    }

    public void changeVertical(){
        //Not implemented yet
    }

    public void changeHorizontal(){
        //Not implemented yet
    }

    public boolean isCollision() {
        return false; //Not implemented yet
    }


}
