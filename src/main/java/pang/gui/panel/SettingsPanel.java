package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.hardware.Audio;
import pang.hardware.Screen;

import javax.swing.*;

public class SettingsPanel extends PangPanel {
    SettingsPanel(Screen screen, Audio audio) {
        super("Settings");
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        JButton musicButton = new JButton("Turn On/Off music");

        musicButton.addActionListener(e -> {
            if(audio.getAudioStatus()) {
                audio.getAudio().pause();
            }
            else if(!audio.getAudioStatus()) {
                audio.getAudio().resumeLoop();
            }
        });


        add(musicButton);
        add(backButton);

    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("Settings");
    }
}
