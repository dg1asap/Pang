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

public class GameplayPanel extends PangPanel implements KeyListener {
    private final GameMessageDialog messageDialog = new GameMessageDialog();
    private Timer gameTimer;
    private GameConfig keyboardConfig;
    private World world;
    private Path levelPath;
    private long gameTime = 0;

    public GameplayPanel(Screen screen) {
        super("Gameplay");
        setLevelNameAndPathFromUserChoice(screen);
        loadOfflineConfig();
        loadUserControl();
        loadWorld();
        addResizeObserverToSubObjects(screen);
        turnOnTimeControl(screen);
    }

    public void paint (Graphics g) {
        super.paintComponent(g);
        world.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        pauseActivation(e.getKeyChar());
        ifPauseNotActivatedSteer(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public boolean hasKeyListener(){
        return true;
    }

    @Override
    public KeyListener getKeyListener(){
        return this;
    }

    @Override
    public GameInfo getGameInfo() {
        GameInfo worldInfo = world.getGameInfo();
        GameplayInfoFactory infoFactory = new GameplayInfoFactory();
        infoFactory.update(worldInfo);
        infoFactory.update(panelInfo);
        return infoFactory.create(this);
    }

    private void loadOfflineConfig() {
        Path path = Path.of("./data/main/configs.txt");
        ConfigLoader.CONFIG_LOADER.init(path);
    }

    private void loadUserControl(){
        keyboardConfig = ConfigLoader.CONFIG_LOADER.getConfig("Keyboard");
    }

    private void loadWorld() {
        WorldLoader worldLoader = new WorldLoader(levelPath);
        world = worldLoader.getWorld();
    }

    private void setLevelNameAndPathFromUserChoice(Screen screen) {
        GameInfo screenInfo = screen.getGameInfo();
        levelPath = Path.of(screenInfo.getAttribute("levelPath"));
    }

    private void addResizeObserverToSubObjects(Screen screen) {
        screen.addResizeObserver(world);
    }

    private void turnOnTimeControl(Screen screen) {
        gameTimer = new Timer(1, taskPerformer -> refresh(screen) );
        gameTimer.start();
    }

    private void pauseActivation(char keyChar) {
        if ( isPauseKey(keyChar) && isPause())
            gameTimer.start();
        else if(isPauseKey(keyChar))
            gameTimer.stop();
    }

    private boolean isPauseKey(char keyChar) {
        return keyChar == 'p' || keyChar == 'P';
    }

    private boolean isPause() {
        return !gameTimer.isRunning();
    }

    private void ifPauseNotActivatedSteer(char keyChar) {
        if (!isPause()) {
            ifKeyCharHasConfigSteer(keyChar);
        }
    }

    private void ifKeyCharHasConfigSteer(char keyChar) {
        if (isConfigForKeyChar(keyChar)) {
            double value = keyboardConfig.getAttribute(String.valueOf(keyChar));
            world.steerKey(keyChar, value);
        }
    }

    private boolean isConfigForKeyChar(char keyChar) {
        PlayerReaction playerReaction = new PlayerReaction();
        return !"none".equals(playerReaction.fromKeyName(keyChar));
    }

    private void refresh(Screen screen){
        quit(screen);
        renderGUI(screen);
        steerTime();
        repaint();
    }

    private void quit(Screen screen){
        if (!canSteerTime()) {
            saveData(screen);
            turnOffRefresh();
        }
    }

    private void saveData(Screen screen) {
        GameInfo screenInfo = screen.getGameInfo();
        String levelName = screenInfo.getAttribute("levelName");

        GameInfo worldInfo = world.getGameInfo();
        String scoreWithBonus = worldInfo.getAttribute("scoreWithBonus");

        ScoreSaver scoreSaver = new ScoreSaver(levelName, Double.parseDouble(scoreWithBonus));
        scoreSaver.save();
    }

    private void turnOffRefresh() {
        gameTimer.stop();
    }

    private void renderGUI(Screen screen) {
        messageDialog.showMessageDialog(world.getGameInfo());
        screen.loadNextPanel();
    }

    private void steerTime() {
        if (canSteerTime()) {
            gameTime += 1;
            world.steerTime(gameTime);
        }
    }

    private boolean canSteerTime() {
        return !world.isGameOver() && !world.isEmpty();
    }


}