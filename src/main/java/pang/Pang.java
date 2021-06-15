package pang;

import pang.gui.panel.PanelCreator;
import pang.hardware.Audio;
import pang.hardware.Screen;


/**
 * Klasa reprezentująca grę
 */
public class Pang {
    /**
     * Ekran programu
     */
    private final Screen screen = new Screen();
    /**
     * Muzyka, która gra w tle
     */
    private final Audio music = new Audio();

    /**
     * Tworzy grę
     */
    public Pang() {
        loadScreen();
    }

    /**
     * Ładuje komponent w screenie, który posłuży do produkcji paneli
     */
    private void loadScreen() {
        PanelCreator panelCreator = new PanelCreator(screen, music);
        screen.setPanelCreator(panelCreator);
    }


}