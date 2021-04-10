package pang.backend;

public class Player extends Character implements Movement {

    int ammoAmount;
    double gravityForce;


    public Player(GameConfig config){
        this.health = config.getAttribute("health");
        this.damage = config.getAttribute("damage");
        this.speed = config.getAttribute("speed");

        ammoAmount = config.getAttribute("ammunition");
        gravityForce = config.getAttribute("gravity");

        this.position.setHorizontal(config.getAttribute("startX"));
        this,position.setVertical(config.getAttribute("startY"));
    }

    void shoot(){
        ammoAmount += -1;
    }

    int getAmmoAmount(){
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
        return false;
    }
    

}
