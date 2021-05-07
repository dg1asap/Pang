package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public class LevelPanel extends PangPanel {
    public LevelPanel(Screen screen) {
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
}




