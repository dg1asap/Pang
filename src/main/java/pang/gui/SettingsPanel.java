package pang.gui;

import pang.hardware.Audio;
import pang.hardware.Screen;

import javax.swing.*;

public class SettingsPanel extends PangPanel {
    SettingsPanel(Screen screen, Audio audio) {
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
}
