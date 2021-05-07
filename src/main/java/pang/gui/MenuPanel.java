package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public class MenuPanel extends PangPanel {
    MenuPanel(Screen screen) {
        JButton playButton = createButtonToChangeWindowTo("Play","UserData", screen);
        JButton levelButton = createButtonToChangeWindowTo("Level", "Level", screen);
        JButton scoreButton = createButtonToChangeWindowTo("High scores", "HighScores", screen);
        JButton settingsButton = createButtonToChangeWindowTo("Settings","Settings", screen);

        JButton quitButton = new JButton("QUIT");
        quitButton.addActionListener(e -> System.exit(0));

        add(playButton);
        add(levelButton);
        add(scoreButton);
        add(settingsButton);
        add(quitButton);
    }
}
