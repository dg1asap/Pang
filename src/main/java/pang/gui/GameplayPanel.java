package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public class GameplayPanel extends PangPanel{
    GameplayPanel(Screen screen) {
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        add(backButton);
    }
}
