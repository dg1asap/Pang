package pang.backend.character;

public class CharacterPosition {
    private double vertical;
    private double horizontal;

    public double getVertical(){
        return vertical;
    }

    public double getHorizontal(){
        return horizontal;
    }

    public void setVertical(double posY){
        vertical = posY;
    }

    public void setHorizontal(double posX){
        horizontal = posX;
    }

    public void changeVertical(double dy) {
        vertical += dy;
    }

    public void changeHorizontal(double dx) {
        horizontal += dx;
    }
}
