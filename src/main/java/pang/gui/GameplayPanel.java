package pang.gui;

import pang.backend.character.player.PlayerReaction;
import pang.backend.config.ConfigLoader;
import pang.backend.config.GameConfig;
import pang.backend.world.World;
import pang.backend.world.WorldLoader;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;

public class GameplayPanel extends PangPanel implements KeyListener {

    private static Path levelPath;
    private static World world;

    private final GameConfig keyboardConfig;
    private final Timer gameTimer;
    private long gameTime = 0;

    public GameplayPanel(Screen screen) {
        Path configPath = Path.of("./data/main/configs.txt");
        ConfigLoader configLoader = ConfigLoader.fromConfigPath(configPath);
        keyboardConfig = configLoader.getConfig("Keyboard");

        loadConfig();

        gameTimer = new Timer(1, taskPerformer -> refresh(screen) );
        gameTimer.start();
        //JButton backButton = createButtonToChangeWindowTo("Back", "Menu", screen);
        //add(backButton);
    }

    public static void setLevelPath(Path path){
        levelPath = path;
        System.out.println("Loading level: path -> " + path);
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

    private static void loadConfig() {
        Path defaultConfigPath = Path.of("./data/main/configs.txt");
        Path defaultLevelPath = levelPath;
        WorldLoader worldLoader = WorldLoader.fromConfigPathAndLevelPath(defaultConfigPath, defaultLevelPath);
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
        if(!world.isGameOver() && !world.isEmpty()) {
            gameTime += 1;
            resizePanel();
            world.steerTime(gameTime);
            repaint();
        }
        else if(world.isEmpty()){
            gameTimer.stop();
            JOptionPane.showMessageDialog(null,"Press Ok to return to menu", "Congratulations! YOU WON", JOptionPane.PLAIN_MESSAGE);
            screen.render("Menu");
        }
        else{
            gameTimer.stop();
            JOptionPane.showMessageDialog(null,"Press Ok to return to menu","GAME OVER", JOptionPane.PLAIN_MESSAGE);
            screen.render("Menu");
        }
    }

    private void resizePanel(){
        PangFrame.setActualScreenHeight(this.getHeight());
        PangFrame.setActualScreenWidth(this.getWidth());
    }

}




