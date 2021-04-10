package pang.backend;

public class Player extends Character implements Movement {

    int ammoAmount;
    double gravityForce;
    double dx = 1;
    double dy = 1;

    public Player(GameConfig config){
        this.setHealth(config.getAttribute("health"));
        this.setDamage(config.getAttribute("damage"));
        this.setSpeed(config.getAttribute("speed"));

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


}
