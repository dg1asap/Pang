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

/**
 * Menadżer zmiany paneli
 */
public class Screen implements ActionListener, Info, ResizeObservable {
    /**
     * obserwatorzy zmiany rozmiru
     */
    private final List<ResizeObserver> observers = new ArrayList<>();
    /**
     * główne okno
     */
    private final PangFrame mainWindow = new PangFrame(this);
    /**
     * kreator paneli
     */
    private PanelCreator panelCreator;
    /**
     * aktualnie wybrany panel
     */
    private PangPanel currentPanel;
    /**
     * info o screenie
     */
    private GameInfo screenInfo;
    /**
     * pole z aktualnym poziomem trudności
     */
    private JLabel actualLevel;

    /**
     * tworzy menadżera zmiany paneli i łąduje domyślnie menu panel jak panel powitalny
     */
    public Screen() {
        initTools();
        loadNextPanel("Menu");
        makeLabels();
    }

    /**
     * dodaje obserwatora zmiany rozmiaru oraz inicjuje resize
     * @param observer obiekt klasy nasłuchującej zmianę rozmiru
     */
    @Override
    public void addResizeObserver(ResizeObserver observer) {
        this.observers.add(observer);
        observer.initialResize(mainWindow.getFrameSize());
    }

    /**
     * usunięcie obserwatora zmiany rozmiru
     * @param observer obiekt klasy nasłuchującej zmianę rozmiaru
     */
    @Override
    public void removeResizeObserver(ResizeObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * metoda powiadamiająca nasłuchujących zmiany rozmiaru
     */
    @Override
    public void resizeNotify() {
        for (ResizeObserver observer : this.observers) {
            observer.resize(mainWindow.getFrameSize());
        }
    }

    /**
     * ustawia kreatora paneli
     * @param panelCreator kreator paneli
     */
    public void setPanelCreator(PanelCreator panelCreator) {
        this.panelCreator = panelCreator;
    }

    /**
     * ładuje ostatnio usawiony panel
     */
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

    /**
     * ładuje panel o nazwię będącej argumentem metody
     * @param panelName nazwa następnego panelu
     */
    public void loadNextPanel(String panelName) {
        observers.clear();
        selectPanel(panelName);
        setPanel();
        activePanel();
    }

    /**
     * @return zwraca pole wybranego poziomu trudności
     */
    public JLabel getActualLevel() {
        return actualLevel;
    }

    /**
     * metoda wywoływana na podpięte do obiektu this zmianę panelu
     * @param e zdarzenie
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String panelName = e.getActionCommand();
        loadNextPanel(panelName);
    }

    /**
     * zwraca inforamcje o screenie
     * @return obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        return screenInfo;
    }

    /**
     * inicjuje narzędzia takie jak panel kreator i screenInfo
     */
    private void initTools() {
        this.panelCreator = new PanelCreator(this);
        this.screenInfo = new GameInfo("Screen");
    }

    /**
     * wybiera następny panel ale no nie zładowuje
     * @param panelName nazwa wybranego następny
     */
    private void selectPanel(String panelName) {
        screenInfo.addAttribute("nextPanel", panelName);
    }

    /**
     * załadowuje ostanio wybrany panel
     */
    private void setPanel(){
        currentPanel = panelCreator.getNextPanel();
        mainWindow.setPanel(currentPanel);
    }

    /**
     * aktywuje panel poprzez pdłączenie go do listenera'a
     */
    private void activePanel() {
        activeKeyListener();
    }

    /**
     * podpina aktualny panel do frame (jako listener)
     */
    private void activeKeyListener() {
        if (currentPanel.hasKeyListener())
            mainWindow.addKeyListener(currentPanel.getKeyListener());
    }

    /**
     * ustawia domyślny poziom trudności na normalny
     */
    private void makeLabels() {
        actualLevel = new JLabel("Default difficulty level: NORMAL");
    }


}