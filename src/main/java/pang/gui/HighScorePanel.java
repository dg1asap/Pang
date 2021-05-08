package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public class HighScorePanel extends PangPanel {
    public HighScorePanel(Screen screen) {
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        JLabel highScore = new JLabel("Best players:");
        add(highScore);
        add(backButton);
    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

}
