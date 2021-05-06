package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;

public abstract class PangPanel extends JPanel {
    public JButton createButtonToChangeWindowTo(String buttonName, String panelName, Screen screen) {
        JButton button = new JButton(buttonName);
        button.addActionListener(screen);
        button.setActionCommand(panelName);
        return button;
    }
}
