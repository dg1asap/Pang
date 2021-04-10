package pang.backend;

public class Character {
    double health;
    double damage;
    double speed;

    CharacterPosition position;

    boolean isAlive(){
        return health > 0;
    }

}
