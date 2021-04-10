package pang.backend;

public class CharacterPosition {
    double vertical;
    double horizontal;

    double getVertical(){
        return vertical;
    }

    double getHorizontal(){
        return horizontal;
    }

    public void setVertical(double posY){
        vertical = posY;
    }

    public void setHorizontal(double posX){
        horizontal = posX;
    }
}
