package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public class SettingsPanel extends PangPanel {
    SettingsPanel(Screen screen) {
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        JButton musicButton = new JButton("musicButton");
        musicButton.addActionListener(e -> System.out.println("1223"));
        add(backButton);
        add(musicButton);
    }
}
