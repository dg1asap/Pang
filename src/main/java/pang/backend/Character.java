package pang.backend;

public class Character {

    protected double health;
    protected double damage;
    protected double speed;

    CharacterPosition position = new CharacterPosition();

    public void setHealth(double health){
        this.health = health;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    double getYPosition(){
        return position.getVertical();
    }

    double getXPosition(){
        return position.getHorizontal();
    }

    public void setPosX(double posX){
        position.setHorizontal(posX);
    }

    public void setPosY(double posY){
        position.setVertical(posY);
    }

    public void changePosX(double dx){
        position.changeHorizontal(dx);
    }

    public void changePosY(double dy){
        position.changeVertical(dy);
    }

    public boolean isAlive(){
        return health > 0;
    }
}
