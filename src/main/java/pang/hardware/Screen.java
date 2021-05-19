package pang.hardware;

import pang.gui.panel.PanelCreator;
import pang.gui.frame.PangFrame;
import pang.gui.panel.PangPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen implements ActionListener {
    private PangFrame mainWindow = new PangFrame();
    private PanelCreator panelCreator;
    private JLabel actualLevel;

    public Screen() {
        this.panelCreator = new PanelCreator(this);
        render("Menu");
        makeLabels();
    }

    public void setPanelCreator(PanelCreator panelCreator) {
        this.panelCreator = panelCreator;
    }

    public void render(String panelName) {
        PangPanel newPanel = panelCreator.create(panelName);
        mainWindow.setPanel(newPanel);

        if (newPanel.hasKeyListener()) {
            mainWindow.addKeyListener(newPanel.getKeyListener());
        }
    }

    private void makeLabels() {
        actualLevel = new JLabel("Default difficulty level: NORMAL");
    }

    public JLabel getActualLevel() {
        return actualLevel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        render(e.getActionCommand());
    }

}
