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

    void setVertical(double posY){
        this.vertical = posY;
    }

    void setHorizontal(double posX){
        this.horizontal = posX;
    }
}
