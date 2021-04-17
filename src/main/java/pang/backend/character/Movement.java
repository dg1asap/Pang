package pang.backend.character;

public interface Movement {
    void changeXDirection();
    void changeYDirection();
    boolean isCollision();
}
