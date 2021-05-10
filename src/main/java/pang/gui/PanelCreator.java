package pang.gui;

import pang.hardware.Audio;
import pang.hardware.Screen;

public class PanelCreator {

    private Screen screen;
    private Audio audio;

    public PanelCreator(Screen screen, Audio audio){
        this.screen = screen;
        this.audio = audio;

    }

    public PanelCreator(Screen screen){
        this.screen = screen;
    }

    public PangPanel create(String panelName) throws IllegalArgumentException {
        return switch (panelName){
            case "Menu" -> new MenuPanel(screen);
            case "UserData" -> new UserDataPanel(screen);
            case "Gameplay" -> new GameplayPanel(screen);
            case "Settings" -> new SettingsPanel(screen, audio);
            case "Level" -> new LevelPanel(screen);
            case "HighScores" -> new HighScorePanel(screen);
            default -> throw new IllegalArgumentException();
        };
    }

}
