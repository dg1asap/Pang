package pang.backend;

public interface Movement {
    void changeVertical();
    void changeHorizontal();
    boolean isCollision();
}
