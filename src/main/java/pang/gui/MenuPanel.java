package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuPanel extends PangPanel {
    MenuPanel(Screen screen) {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(0,10,200,30));

        JButton playButton = createButtonToChangeWindowTo("Play","UserData", screen);
        JButton levelButton = createButtonToChangeWindowTo("Level", "Level", screen);
        JButton scoreButton = createButtonToChangeWindowTo("High scores", "HighScores", screen);
        JButton settingsButton = createButtonToChangeWindowTo("Settings","Settings", screen);

        JButton quitButton = new JButton("QUIT");
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setForeground(new Color(0xFF0000));


        add(playButton);
        add(levelButton);
        add(scoreButton);
        add(settingsButton);
        add(quitButton);

    }
}
