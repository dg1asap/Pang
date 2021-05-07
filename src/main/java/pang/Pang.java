package pang;

import pang.gui.PanelCreator;
import pang.hardware.Audio;
import pang.hardware.Screen;

public class Pang {
    private Screen screen;
    private Audio music;

    Pang(){
        screen = new Screen();
        music = new Audio();

        PanelCreator panelCreator = new PanelCreator(screen, music);
        screen.setPanelCreator(panelCreator);
    }

}

