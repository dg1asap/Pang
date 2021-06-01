package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class PangPanel extends JPanel implements Info {
    protected GameInfo panelInfo;

    public PangPanel(String panelName) {
        panelInfo = new GameInfo(panelName);
    }

    public JButton createButtonToChangeWindowTo(String buttonName, String panelName, Screen screen) {
        JButton button = new JButton(buttonName);
        button.setActionCommand(panelName);
        button.addActionListener(screen);
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
