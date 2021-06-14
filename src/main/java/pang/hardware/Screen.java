package pang.hardware;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.backend.util.ResizeObservable;
import pang.backend.util.ResizeObserver;
import pang.gui.panel.PanelCreator;
import pang.gui.frame.PangFrame;
import pang.gui.panel.PangPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Screen implements ActionListener, Info, ResizeObservable {
    private final List<ResizeObserver> observers = new ArrayList<>();
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
    public void addResizeObserver(ResizeObserver observer) {
        this.observers.add(observer);
        observer.initialResize(mainWindow.getFrameSize());
    }

    @Override
    public void removeResizeObserver(ResizeObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void resizeNotify() {
        for (ResizeObserver observer : this.observers) {
            observer.resize(mainWindow.getFrameSize());
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