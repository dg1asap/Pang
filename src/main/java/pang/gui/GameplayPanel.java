package pang.gui;

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
    InfoInGame infoInGame;
    GameConfig keyboardConfig;
    World world;

    GameplayPanel(Screen screen) {
        Path configPath = Path.of("./data/main/configs.txt");
        ConfigLoader configLoader = ConfigLoader.fromConfigPath(configPath);
        keyboardConfig = configLoader.getConfig("Keyboard");

        infoInGame = new InfoInGame(screen);

        loadConfig();
        JButton backButton = createButtonToChangeWindowTo("Back", "Menu", screen);
        add(backButton);

    }

    public void paint (Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(100, 100, 20, 20);
        infoInGame.paint(g);
    }


    private void loadConfig() {
        Path defaultConfigPath = Path.of("./data/main/configs.txt");
        Path defaultLevelPath = Path.of("./data/main/level/1.txt");
        WorldLoader worldLoader = WorldLoader.fromConfigPathAndLevelPath(defaultConfigPath, defaultLevelPath);
        world = worldLoader.getWorld();
        System.out.println("Config loaded");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        String keyName = e.toString();
        double value = keyboardConfig.getAttribute(keyName);
        world.steer(keyName,value);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}




