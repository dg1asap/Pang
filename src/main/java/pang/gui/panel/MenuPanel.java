package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel menu głównego
 */
public class MenuPanel extends PangPanel {
    /**
     * Tworzy panel menu głównego
     * @param screen menadżer zmiany panelu
     */
    MenuPanel(Screen screen) {
        super("Menu");
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(0,10,200,30));

        JButton playButton = createButtonToChangeWindowTo("Play","UserData", screen);
        JButton levelButton = createButtonToChangeWindowTo("Level", "Level", screen);
        JButton scoreButton = createButtonToChangeWindowTo("High scores", "HighScores", screen);
        JButton settingsButton = createButtonToChangeWindowTo("Settings","Settings", screen);
        JButton onlineButton = createButtonToChangeWindowTo("Online","Online", screen);

        JButton quitButton = new JButton("QUIT");
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setForeground(new Color(0xFF0000));

        add(playButton);
        add(levelButton);
        add(scoreButton);
        add(settingsButton);
        add(onlineButton);
        add(quitButton);

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
     * Zwraca informację z działaniem klasy MenuPanel
     * @return zwraca obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("Menu");
    }
}
