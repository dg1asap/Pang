package pang.backend;

public class Player extends Character implements Movement {

    double ammoAmount;
    double gravityForce;

    public Player(GameConfig config){
        this.setHealth(config.getAttribute("health"));
        this.setDamage(config.getAttribute("damage"));
        this.setSpeed(config.getAttribute("speed"));

        ammoAmount = config.getAttribute("ammunition");
        gravityForce = config.getAttribute("gravityForce");

        this.setPosX(config.getAttribute("startPosX"));
        this.setPosY(config.getAttribute("startPosY"));
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
