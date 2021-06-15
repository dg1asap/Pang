package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.gui.panel.onlinePanels.OnlineGameplayPanel;
import pang.gui.panel.onlinePanels.OnlineHighScorePanel;
import pang.gui.panel.onlinePanels.OnlinePanel;
import pang.gui.panel.onlinePanels.OnlineUserDataPanel;
import pang.hardware.Audio;
import pang.hardware.Screen;

/**
 * Kreator paneli
 */
public class PanelCreator {

    /**
     * Menadżer zmiany panelu
     */
    private final Screen screen;
    /**
     * Obiekt przechowujący muzykę
     */
    private Audio audio;

    /**
     * Przypisuje menadżera zmiany panelu oraz obiekt przechowujący muzykę do obiektu zmieniającego panele
     * @param screen menadżer zmiany panelu
     * @param audio obiekt przechowujący muzykę
     */
    public PanelCreator(Screen screen, Audio audio){
        this.screen = screen;
        this.audio = audio;
    }

    /**
     * Przypisuje menadżera zmiany panelu do obiektu zmieniającego panele
     * @param screen menadżer zmiany panelu
     */
    public PanelCreator(Screen screen){
        this.screen = screen;
    }

    /**
     * Tworzy nowy PangPanel
     * @return zwraca następny PangPanel
     * @throws IllegalArgumentException
     */
    public PangPanel getNextPanel() throws IllegalArgumentException {
        String nextPanelName = getNextPanelName();
        return createPanel(nextPanelName);
    }

    /**
     * Pobiera atrybut z nazwą panelu
     * @return zwraca nazwę następnego panelu jaki chcemy
     */
    public String getNextPanelName() {
        GameInfo screenInfo = screen.getGameInfo();
        return screenInfo.getAttribute("nextPanel");
    }

    /**
     * Pobiera nazwę panelu i tworzy panel dostepny pod taką nazwą
     * @param nextPanelName nazwa panelu jaki chcemy stworzyć
     * @return zwraca nowy panel
     */
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
