package pang.gui;

import pang.backend.config.ConfigLoader;
import pang.backend.config.GameConfig;
import pang.backend.world.World;
import pang.backend.world.WorldLoader;
import pang.hardware.Screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;

public class GameplayPanel extends PangPanel implements KeyListener {
    private GameConfig keyboardConfig;
    private World world;

    public GameplayPanel(Screen screen) {
        Path configPath = Path.of("./data/main/configs.txt");
        ConfigLoader configLoader = ConfigLoader.fromConfigPath(configPath);
        keyboardConfig = configLoader.getConfig("Keyboard");

        loadConfig();
        //JButton backButton = createButtonToChangeWindowTo("Back", "Menu", screen);
        //add(backButton);
    }

    public void paint (Graphics g) {
        super.paintComponent(g);
        world.draw(g);
    }

    private void loadConfig() {
        Path defaultConfigPath = Path.of("./data/main/configs.txt");
        Path defaultLevelPath = Path.of("./data/main/level/1.txt");
        WorldLoader worldLoader = WorldLoader.fromConfigPathAndLevelPath(defaultConfigPath, defaultLevelPath);
        world = worldLoader.getWorld();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        double value = keyboardConfig.getAttribute(String.valueOf(keyChar));
        world.steer(keyChar, value);
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

}




