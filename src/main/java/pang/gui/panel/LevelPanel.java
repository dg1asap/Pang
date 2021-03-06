package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.hardware.Screen;

import javax.swing.*;

/**
 * Panel pozwalający wybrać poziom trudności
 */
public class LevelPanel extends PangPanel {
    /**
     * Tworzy panel do zmiany poziomu trudności
     * @param screen menadżer zmiany panelu
     */
    public LevelPanel(Screen screen) {
        super("Level");
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        JLabel chooseLevel = new JLabel("Choose difficulty:");

        JButton easyLevel = new JButton("EASY");
        JButton normalLevel = new JButton("NORMAL");
        JButton hardLevel = new JButton("HARD");

        easyLevel.addActionListener(e -> screen.getActualLevel().setText("Selected difficulty level: EASY"));
        normalLevel.addActionListener(e -> screen.getActualLevel().setText("Selected difficulty level: NORMAL"));
        hardLevel.addActionListener(e -> screen.getActualLevel().setText("Selected difficulty level: HARD"));

        add(chooseLevel);
        add(easyLevel);
        add(normalLevel);
        add(hardLevel);
        add(screen.getActualLevel());
        add(backButton);
    }

    /**
     * Metoda zwracająca false, ponieważ klasa nie posiada żadnego KeyListenera
     * @return zwraca false
     */
    @Override
    public boolean hasKeyListener() {
        return false;
    }

    /**
     * Zwraca informację z działaniem klasy LevelPanel
     * @return zwraca obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("Level");
    }
}
