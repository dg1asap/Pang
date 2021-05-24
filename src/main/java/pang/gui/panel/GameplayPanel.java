package pang.gui.panel;

import pang.backend.character.player.PlayerReaction;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.info.GameInfo;
import pang.backend.world.World;
import pang.backend.world.WorldLoader;
import pang.gui.frame.PangFrame;
import pang.gui.messageDialog.GameMessageDialog;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class GameplayPanel extends PangPanel implements KeyListener {
    private GameMessageDialog messageDialog = new GameMessageDialog();
    private final Timer gameTimer;
    private GameConfig keyboardConfig;
    private World world;
    private Path levelPath;
    private String levelName;
    private long gameTime = 0;

    public GameplayPanel(Screen screen) {
        super("Gameplay");
        loadUserControl();
        getLevelNameAndPathFromUserChoice(screen);
        loadWorld();

        gameTimer = new Timer(1, taskPerformer -> refresh(screen) );
        gameTimer.start();
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
        if (worldInfo.hasAttribute("ending")) {
            String ending = worldInfo.getAttribute("ending");
            if (ending.equals("win")) {
                panelInfo.addAttribute("nextPanel", "Menu");
            }

            if (ending.equals("lose")) {
                panelInfo.addAttribute("nextPanel", "Menu");
            }
        }

        return panelInfo;
    }

    private void loadUserControl(){
        keyboardConfig = ConfigLoader.CONFIG_LOADER.getConfig("Keyboard");
    }

    private void getLevelNameAndPathFromUserChoice(Screen screen) {
        GameInfo screenInfo = screen.getGameInfo();
        levelName = screenInfo.getAttribute("levelName");
        levelPath = Path.of(screenInfo.getAttribute("levelPath"));
        System.out.println("Loading level: path -> " + levelPath);
    }

    private void loadWorld() {
        WorldLoader worldLoader = new WorldLoader(levelPath);
        world = worldLoader.getWorld();
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
        if (gameTimer.isRunning()) {
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
        quit();
        renderGUI(screen);
        steerTime();
        repaint();
    }

    private void quit(){
        if (!canSteerTime())
            gameTimer.stop();
    }

    private void renderGUI(Screen screen) {
        messageDialog.showMessageDialog(world.getGameInfo());
        screen.loadNextPanel();
        resizePanel();
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

    private void saveScore(){
        File highScores = Path.of("data","main", "highScores", levelName + ".txt").toFile();
        try{
            int levelScore = 10;
            if(highScores.exists()){
                FileWriter score = new FileWriter(highScores, true);
                score.write("\n" + UserDataPanel.getUserName() + " " + levelScore);
                score.close();
                System.out.println("Added score to file: " + levelName);
            }
            else{
                FileWriter score = new FileWriter(highScores);
                score.write(UserDataPanel.getUserName() + " " + levelScore);
                score.close();
                System.out.println("Created new HighScores file: " + levelName);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void resizePanel(){
        PangFrame.setActualScreenHeight(this.getHeight());
        PangFrame.setActualScreenWidth(this.getWidth());
    }


}