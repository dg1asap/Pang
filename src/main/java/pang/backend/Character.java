package pang.backend;

public class Character {
    long health;
    long damage;
    long speed;

    CharacterPosition position;

    boolean isAlive(){
        return health > 0;
    }

}
