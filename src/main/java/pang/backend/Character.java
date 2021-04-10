package pang.backend;

public class Character {
    double health;
    double damage;
    double speed;

    CharacterPosition position = new CharacterPosition();

    public boolean isAlive(){
        return health > 0;
    }
}
