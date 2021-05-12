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
    private final GameConfig keyboardConfig;
    private World world;
    private final Timer gameTimer;
    private long gameTime = 0;

    public GameplayPanel(Screen screen) {
        Path configPath = Path.of("./data/main/configs.txt");
        ConfigLoader configLoader = ConfigLoader.fromConfigPath(configPath);
        keyboardConfig = configLoader.getConfig("Keyboard");

        loadConfig();

        gameTimer = new Timer(1, taskPerformer -> refresh() );
        gameTimer.start();
        //JButton backButton = createButtonToChangeWindowTo("Back", "Menu", screen);
        //add(backButton);
    }

    public void paint (Graphics g) {
        super.paintComponent(g);
        world.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        ifKeyCharHasConfigSteer(keyChar);
        repaint(); //TODO jest ok ale w sumie nie nie ten poziom abstrakcji, do zmiany żeby lepeij wyglądało
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public boolean hasKeyListener(){
        return true;
    }


    @Override
    public KeyListener getKeyListener(){
        return this;
    }

    private void loadConfig() {
        Path defaultConfigPath = Path.of("./data/main/configs.txt");
        Path defaultLevelPath = Path.of("./data/main/level/1.txt");
        WorldLoader worldLoader = WorldLoader.fromConfigPathAndLevelPath(defaultConfigPath, defaultLevelPath);
        world = worldLoader.getWorld();
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

    private void refresh(){
        gameTime += 100;
        resizePanel();
        world.steerTime(gameTime);
        repaint();
    }

    private void resizePanel(){
        /*
        setActualScreenHeight(this.getHeight());
        setActualScreenWidth(this.getWidth());
        getExtremePointOfFrame();
         */
    }

}




