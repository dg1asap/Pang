package pang;

import pang.backend.properties.config.ConfigLoader;
import pang.gui.panel.PanelCreator;
import pang.hardware.Audio;
import pang.hardware.Screen;

import java.nio.file.Path;

public class Pang {
    private final Screen screen = new Screen();
    private final Audio music = new Audio();

    Pang(){
        loadConfigs();
        loadScreen();
    }

    private void loadConfigs() {
        Path path = Path.of("./data/main/configs.txt");
        ConfigLoader.CONFIG_LOADER.init(path);
    }

    private void loadScreen() {
        PanelCreator panelCreator = new PanelCreator(screen, music);
        screen.setPanelCreator(panelCreator);
    }


}