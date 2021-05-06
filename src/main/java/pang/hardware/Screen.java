package pang.hardware;

import pang.gui.PanelCreator;
import pang.gui.PangPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen implements ActionListener {
    JFrame mainWindow = new JFrame();
    PanelCreator panelCreator = new PanelCreator();

    public Screen() {
        PangPanel startPanel = panelCreator.create("Menu",this);
        mainWindow.setPreferredSize(new Dimension(500, 500));
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.pack();
        mainWindow.setContentPane(startPanel);
        mainWindow.setVisible(true);
    }

    public void render(String panelName){
        PangPanel newPanel = panelCreator.create(panelName, this);
        mainWindow.setContentPane(newPanel);
        mainWindow.getContentPane().repaint();
        mainWindow.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        render(e.getActionCommand());
    }
}
