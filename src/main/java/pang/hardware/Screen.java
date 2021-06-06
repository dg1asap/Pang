package pang.hardware;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.backend.util.PangObservable;
import pang.backend.util.PangObserver;
import pang.gui.panel.PanelCreator;
import pang.gui.frame.PangFrame;
import pang.gui.panel.PangPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Screen implements ActionListener, Info, PangObservable {
    private List<PangObserver> observers = new ArrayList<>();
    private final PangFrame mainWindow = new PangFrame(this);
    private PanelCreator panelCreator;
    private PangPanel currentPanel;
    private GameInfo screenInfo;
    private JLabel actualLevel;

    public Screen() {
        initTools();
        loadNextPanel("Menu");
        makeLabels();
    }

    @Override
    public void addPangObserver(PangObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removePangObserver(PangObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyPang() {
        for (PangObserver observer : this.observers) {
            observer.pangUpdate();
        }
    }

    public void setPanelCreator(PanelCreator panelCreator) {
        this.panelCreator = panelCreator;
    }

    public void loadNextPanel() {
        GameInfo panelInfo = currentPanel.getGameInfo();
        if (panelInfo.hasAttribute("nextPanel")) {
            String panelName = panelInfo.getAttribute("nextPanel");
            observers.clear();
            selectPanel(panelName);
            setPanel();
            activePanel();
        }
    }

    public void loadNextPanel(String panelName) {
        observers.clear();
        selectPanel(panelName);
        setPanel();
        activePanel();
    }

    public JLabel getActualLevel() {
        return actualLevel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String panelName = e.getActionCommand();
        loadNextPanel(panelName);
    }

    @Override
    public GameInfo getGameInfo() {
        return screenInfo;
    }

    private void initTools() {
        this.panelCreator = new PanelCreator(this);
        this.screenInfo = new GameInfo("Screen");
    }

    private void selectPanel(String panelName) {
        screenInfo.addAttribute("nextPanel", panelName);
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