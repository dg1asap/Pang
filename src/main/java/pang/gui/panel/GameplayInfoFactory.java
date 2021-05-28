package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.GameInfoFactory;
import pang.backend.properties.info.Info;

public class GameplayInfoFactory extends GameInfoFactory {
    public GameplayInfoFactory() {
        info = new GameInfo("Gameplay");
    }

    @Override
    public GameInfo create(Info gameplayPanel) {
        if (info.hasAttribute("ending"))
            ifEndingSetNextPanel();
        return info;
    }

    private void ifEndingSetNextPanel() {
        String ending = info.getAttribute("ending");
        if (ending.equals("win") || ending.equals("lose")) {
            info.addAttribute("nextPanel", "Menu");
        }
    }


}
