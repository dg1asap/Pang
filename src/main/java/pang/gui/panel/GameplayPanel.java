package pang.gui.panel;

import pang.backend.character.player.PlayerReaction;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.info.GameInfo;
import pang.backend.util.ScoreSaver;
import pang.backend.world.World;
import pang.backend.world.WorldLoader;
import pang.gui.messageDialog.GameMessageDialog;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;

/**
 * Panel gry w trybie offline
 */
public class GameplayPanel extends PangPanel implements KeyListener {
    /**
     * Okienko wyskakujące po zakończeniu gry
     */
    private final GameMessageDialog messageDialog = new GameMessageDialog();
    /**
     * Czas odmierzany w grze
     */
    private Timer gameTimer;
    /**
     * Konfiguracja sterowania
     */
    private GameConfig keyboardConfig;
    /**
     * Świat gry
     */
    private World world;

    /**
     * ścieżka do poziomu
     */
    private Path levelPath;

    /**
     * Przechowuje aktualny czas w grze
     */
    private long gameTime = 0;

    /**
     * Tworzy panel z grą w trybie offline
     * @param screen menadżer zmiany panelu
     */
    public GameplayPanel(Screen screen) {
        super("Gameplay");
        setLevelNameAndPathFromUserChoice(screen);
        loadOfflineConfig();
        loadUserControl();
        loadWorld();
        addResizeObserverToSubObjects(screen);
        turnOnTimeControl(screen);
    }

    /**
     * Rysuje elementy świata
     * @param g grafika
     */
    public void paint (Graphics g) {
        super.paintComponent(g);
        world.draw(g);
    }

    /**
     * Działanie wykonywane w momencie kliknięcia klawisza (bez klawiszy specjalnych)
     * @param e KeyListener
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Działanie wykonywane w momencie kliknięcia klawisza
     * @param e KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pauseActivation(e.getKeyChar());
        ifPauseNotActivatedSteer(e.getKeyChar());
    }

    /**
     * Działanie wykonywane, kiedy puścimy wciśnięty klawisz
     * @param e KeyListener
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Metoda, która sprawdza, czy klasa posiada KeyListenera
     * @return zwraca true
     */
    @Override
    public boolean hasKeyListener(){
        return true;
    }


    /**
     * Zwraca Keylistenera
     * @return zwraca KeyListener
     */
    @Override
    public KeyListener getKeyListener(){
        return this;
    }

    /**
     * Zwraca informację z działaniem klasy GameplayPanel
     * @return zwraca obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        GameInfo worldInfo = world.getGameInfo();
        GameplayInfoFactory infoFactory = new GameplayInfoFactory();
        infoFactory.update(worldInfo);
        infoFactory.update(panelInfo);
        return infoFactory.create(this);
    }

    /**
     * Ładuje lokalny plik configuracyjny
     */
    private void loadOfflineConfig() {
        Path path = Path.of("./data/main/configs.txt");
        ConfigLoader.CONFIG_LOADER.init(path);
    }

    /**
     * Ładuje config klawiatury
     */
    private void loadUserControl(){
        keyboardConfig = ConfigLoader.CONFIG_LOADER.getConfig("Keyboard");
    }


    /**
     * Ładuje świat
     */
    private void loadWorld() {
        WorldLoader worldLoader = new WorldLoader(levelPath);
        world = worldLoader.getWorld();
    }


    /**
     * Ustawia ścieżkę do poziomu
     * @param screen menadżer zmiany panelu
     */
    private void setLevelNameAndPathFromUserChoice(Screen screen) {
        GameInfo screenInfo = screen.getGameInfo();
        levelPath = Path.of(screenInfo.getAttribute("levelPath"));
    }

    /**
     * Dodaje Obserwatora zmiany rozmiaru okna
     * @param screen menadżer zmiany panelu
     */
    private void addResizeObserverToSubObjects(Screen screen) {
        screen.addResizeObserver(world);
    }

    /**
     * Włącza timer, do odświeżania ekranu
     * @param screen menadżer zmiany panelu
     */
    private void turnOnTimeControl(Screen screen) {
        gameTimer = new Timer(1, taskPerformer -> refresh(screen) );
        gameTimer.start();
    }
    /**
     * Aktywuje pauzę na wciśnięcie klawisza p
     * @param keyChar wciśnięty klawisz
     */
    private void pauseActivation(char keyChar) {
        if ( isPauseKey(keyChar) && isPause())
            gameTimer.start();
        else if(isPauseKey(keyChar))
            gameTimer.stop();
    }

    /**
     * Sprawdza, czy kliknięty został klawisz pauzy
     * @param keyChar wciśnięty klawisz
     * @return true jeśli kliknięte p lub P, w p.p false
     */
    private boolean isPauseKey(char keyChar) {
        return keyChar == 'p' || keyChar == 'P';
    }

    /**
     * Sprawdza, czy jest pauza jest aktywowana
     * @return zwraca stan pauzy
     */
    private boolean isPause() {
        return !gameTimer.isRunning();
    }

    /**
     * Steruje graczem, gdy pauza nie jest włączona
     * @param keyChar wciśnięty klawisz
     */
    private void ifPauseNotActivatedSteer(char keyChar) {
        if (!isPause()) {
            ifKeyCharHasConfigSteer(keyChar);
        }
    }

    /**
     * Steruje graczem, gdy wciśnięty został jakiś klawisz dostępny w configu
     * @param keyChar wciśnięty klawisz
     */
    private void ifKeyCharHasConfigSteer(char keyChar) {
        if (isConfigForKeyChar(keyChar)) {
            double value = keyboardConfig.getAttribute(String.valueOf(keyChar));
            world.steerKey(keyChar, value);
        }
    }

    /**
     * Sprawdza, czy istnieje config dla wciśniętego klawisza
     * @param keyChar wciśnięty klawisz
     * @return zwraca true jeśli istnieje config dla wciśniętego klawisza, w p.p false
     */
    private boolean isConfigForKeyChar(char keyChar) {
        PlayerReaction playerReaction = new PlayerReaction();
        return !"none".equals(playerReaction.fromKeyName(keyChar));
    }


    /**
     * Odświeża ekran
     * @param screen menadżer zmiany panelu
     */
    private void refresh(Screen screen){
        quit(screen);
        renderGUI(screen);
        steerTime();
        repaint();
    }


    /**
     * Kończy rozgrywkę
     * @param screen menadżer zmiany panelu
     */
    private void quit(Screen screen){
        if (!canSteerTime()) {
            saveData(screen);
            turnOffRefresh();
        }
    }


    /**
     * Zapisuje wynik gracza do pliku
     * @param screen menadżer zmiany panelu
     */
    private void saveData(Screen screen) {
        GameInfo screenInfo = screen.getGameInfo();
        String levelName = screenInfo.getAttribute("levelName");

        GameInfo worldInfo = world.getGameInfo();
        String scoreWithBonus = worldInfo.getAttribute("scoreWithBonus");

        ScoreSaver scoreSaver = new ScoreSaver(levelName, Double.parseDouble(scoreWithBonus));
        scoreSaver.save();
    }

    /**
     * Wyłącza odświeżanie ekranu
     */
    private void turnOffRefresh() {
        gameTimer.stop();
    }


    /**
     * Wyświetla okienko pod koniec gry
     * @param screen menadżer zmiany panelu
     */
    private void renderGUI(Screen screen) {
        messageDialog.showMessageDialog(world.getGameInfo());
        screen.loadNextPanel();
    }


    /**
     * Steruje światem zgodnie z płynącym czasem
     */
    private void steerTime() {
        if (canSteerTime()) {
            gameTime += 1;
            world.steerTime(gameTime);
        }
    }

    /**
     * Sprawdza, czy można sterować czasem
     * @return true jeśli można sterować czasem, w p.p false
     */
    private boolean canSteerTime() {
        return !world.isGameOver() && !world.isEmpty();
    }


}