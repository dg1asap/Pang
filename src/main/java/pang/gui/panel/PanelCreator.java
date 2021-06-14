package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.gui.panel.onlinePanels.OnlineGameplayPanel;
import pang.gui.panel.onlinePanels.OnlineHighScorePanel;
import pang.gui.panel.onlinePanels.OnlinePanel;
import pang.gui.panel.onlinePanels.OnlineUserDataPanel;
import pang.hardware.Audio;
import pang.hardware.Screen;

public class PanelCreator {

    private final Screen screen;
    private Audio audio;

    public PanelCreator(Screen screen, Audio audio){
        this.screen = screen;
        this.audio = audio;
    }

    public PanelCreator(Screen screen){
        this.screen = screen;
    }

    public PangPanel getNextPanel() throws IllegalArgumentException {
        String nextPanelName = getNextPanelName();
        return createPanel(nextPanelName);
    }

    public String getNextPanelName() {
        GameInfo screenInfo = screen.getGameInfo();
        return screenInfo.getAttribute("nextPanel");
    }

    public PangPanel createPanel(String nextPanelName) {
        return switch (nextPanelName){
            case "Menu" -> new MenuPanel(screen);
            case "UserData" -> new UserDataPanel(screen);
            case "Gameplay" -> new GameplayPanel(screen);
            case "Settings" -> new SettingsPanel(screen, audio);
            case "Level" -> new LevelPanel(screen);
            case "HighScores" -> new HighScorePanel(screen);
            case "Online" -> new OnlinePanel(screen);
            case "OnlineUserData" -> new OnlineUserDataPanel(screen);
            case "OnlineGameplay" -> new OnlineGameplayPanel(screen);
            case "OnlineHighScore" -> new OnlineHighScorePanel(screen);
            default -> throw new IllegalArgumentException();
        };
    }

}
