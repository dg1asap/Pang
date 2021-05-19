package pang.gui.panel;

import pang.hardware.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class PangPanel extends JPanel {
    public JButton createButtonToChangeWindowTo(String buttonName, String panelName, Screen screen) {
        JButton button = new JButton(buttonName);
        button.addActionListener(screen);
        button.setActionCommand(panelName);
        return button;
    }

    public KeyListener getKeyListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }

    public abstract boolean hasKeyListener();
}
