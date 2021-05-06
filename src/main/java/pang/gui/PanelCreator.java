package pang.gui;

import pang.hardware.Screen;

public class PanelCreator {
    public PangPanel create(String panelName, Screen screen) throws IllegalArgumentException {
        return switch (panelName){
            case "Menu" -> new MenuPanel(screen);
            case "Gameplay" -> new GameplayPanel(screen);
            case "Settings" -> new SettingsPanel(screen);
            default -> throw new IllegalArgumentException();
        };
    }

}
