package pang.backend;

public class Player extends Character implements Movement {

    int ammoAmount;
    CharacterPosition position;

    public Player(GameConfig config){
        this.health = config.getAttribute("health");
        this.damage = config.getAttribute("damege");
        this.speed = config.getAttribute("speed");

        ammoAmount = config.getAttribute("ammunition");

        position.setHorizontal(config.getAttribute("startX"));
        position.setVertical(config.getAttribute("startY"));
    }


}
