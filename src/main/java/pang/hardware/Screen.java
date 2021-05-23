package pang.hardware;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.gui.panel.PanelCreator;
import pang.gui.frame.PangFrame;
import pang.gui.panel.PangPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen implements ActionListener, Info {
    private final PangFrame mainWindow = new PangFrame();
    private PanelCreator panelCreator;
    private PangPanel currentPanel;
    private final GameInfo screenInfo;
    private JLabel actualLevel;

    public Screen() {
        this.panelCreator = new PanelCreator(this);
        screenInfo = new GameInfo("Screen");
        screenInfo.addAttribute("nextPanel", "Menu");
        loadNextPanel();
        makeLabels();
    }

    public void setPanelCreator(PanelCreator panelCreator) {
        this.panelCreator = panelCreator;
    }

    public void loadNextPanel() {
        selectPanel();
        setPanel();
        activePanel();
    }

    public JLabel getActualLevel() {
        return actualLevel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String panelName = e.getActionCommand();
        screenInfo.addAttribute("nextPanel", panelName);
        loadNextPanel();
    }

    @Override
    public GameInfo getGameInfo() {
        return screenInfo;
    }

    private void selectPanel() {
        if (currentPanel != null) {
            GameInfo panelInfo = currentPanel.getGameInfo();
            if (panelInfo.hasAttribute("nextPanel")) {
                screenInfo.addAttribute("nextPanel", panelInfo.getAttribute("nextPanel"));
            }
        }
    }

    private void setPanel(){
        currentPanel = panelCreator.getNextPanel();
        mainWindow.setPanel(currentPanel);
    }

    private void activePanel() {
        activeKeyListener();
    }

    private void activeKeyListener() {
        if (currentPanel.hasKeyListener())
            mainWindow.addKeyListener(currentPanel.getKeyListener());
    }

    private void makeLabels() {
        actualLevel = new JLabel("Default difficulty level: NORMAL");
    }



}
