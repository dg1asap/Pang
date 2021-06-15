package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.GameInfoFactory;
import pang.backend.properties.info.Info;

/**
 * Fabryka raportów GameplayInfo
 */
public class GameplayInfoFactory extends GameInfoFactory {
    /**
     * Tworzy nowy obiekt GameInfo
     */
    public GameplayInfoFactory() {
        info = new GameInfo("Gameplay");
    }

    /**
     * Tworzy raport o stanie gry
     * @param gameplayPanel obiekt implementujący interfejs info
     * @return Obiekt info zawierający raport o stanie gry
     */
    @Override
    public GameInfo create(Info gameplayPanel) {
        if (info.hasAttribute("ending"))
            ifEndingSetNextPanel();
        return info;
    }

    /**
     * Dodaje kolejną informację, na podstawie już istniejących, obrabia dostępne informacje
     */
    private void ifEndingSetNextPanel() {
        String ending = info.getAttribute("ending");
        if (ending.equals("win") || ending.equals("lose")) {
            info.addAttribute("nextPanel", "Menu");
        }
    }


}
