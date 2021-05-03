package pang.backend.character;

import java.awt.event.KeyEvent;

public interface Movement {
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);

    boolean isCollision();
}
