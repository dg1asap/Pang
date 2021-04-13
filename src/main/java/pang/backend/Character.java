package pang.backend;

public class Character {

    protected double health;
    protected double damage;
    protected double speed;
    protected double height;
    protected double width;
    boolean collision = false;
    CharacterPosition position = new CharacterPosition();

    public Character(GameConfig config){
        this.setHealth(config.getAttribute("health"));
        this.setDamage(config.getAttribute("damage"));
        this.setSpeed(config.getAttribute("speed"));
        this.setHeight(config.getAttribute("height"));
        this.setWidth(config.getAttribute("width"));
    }


    public double getHealth(){
        return health;
    }

    public double getDamage(){
        return damage;
    }

    public double getSpeed(){
        return speed;
    }

    public double getHeight(){
        return height;
    }

    public double getWidth(){
        return width;
    }

    public void setHealth(double health){
        this.health = health;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setWidth(double width){
        this.width = width;
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
