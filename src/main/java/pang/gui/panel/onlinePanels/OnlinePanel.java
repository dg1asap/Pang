package pang.gui.panel.onlinePanels;

import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.info.GameInfo;
import pang.gui.panel.PangPanel;
import pang.hardware.Screen;
import pang.online.Client;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class OnlinePanel extends PangPanel{

    public OnlinePanel(Screen screen) {
        super("Level");
        Client client = new Client();
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        JLabel info = new JLabel();

        JLabel serverInfo = new JLabel( client.getConnectionStatus());

        serverInfo.setForeground(Color.red);
        add(serverInfo);
        if(client.getConnectionStatus().equals("Connected to server")){
            serverInfo.setForeground(Color.BLUE);

            JButton levels = createButtonToChangeWindowTo("Play", "OnlineUserData", screen);
            JButton highScores = createButtonToChangeWindowTo("Highscores", "OnlineHighScore", screen);
            JButton configs = new JButton("Configs");

            configs.addActionListener(e -> {
                client.sendCommand("getConfigs");
                client.getData("configs");

                info.setText("Server configs downloaded");
                Path path = Path.of("Downloads", "configs", "serverConfigs.txt");
                ConfigLoader.CONFIG_LOADER.init(path);
                info.setForeground(new Color(0x015006));
                info.setVisible(true);
                add(levels);
            });

            levels.addActionListener(e -> {
                client.sendCommand("getMaps");
                client.getData("maps");
            });


            highScores.addActionListener(e -> {
                client.sendCommand("getHighScores");
                client.getData("highScores");
            });

            add(configs);
            add(highScores);
            backButton.addActionListener(e -> client.sendCommand("logout"));
        }
        add(backButton);
        add(info);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> client.sendCommand("logout")));
    }

    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("Online");
    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

}
