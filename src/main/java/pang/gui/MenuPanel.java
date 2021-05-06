package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public class MenuPanel extends PangPanel {
    MenuPanel(Screen screen) {
        JButton playButton = createButtonToChangeWindowTo("Play","Gameplay", screen);
        JButton settingsButton = createButtonToChangeWindowTo("Settings","Settings", screen);
        add(playButton);
        add(settingsButton);
    }
}
