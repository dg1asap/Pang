package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.hardware.Audio;
import pang.hardware.Screen;

import javax.swing.*;

public class SettingsPanel extends PangPanel {
    SettingsPanel(Screen screen, Audio audio) {
        super("Settings");
        addMusicButton(audio);
        addBackButton(screen);
    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("Settings");
    }

    private void addMusicButton(Audio audio) {
        JButton musicButton = new JButton("Turn On/Off music");
        musicButton.addActionListener(e -> playPauseMusic(audio));
        add(musicButton);
    }

    private void addBackButton(Screen screen) {
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        add(backButton);
    }

    private void playPauseMusic(Audio audio) {
        if(audio.getAudioStatus())
            audio.getAudio().pause();
        else if(!audio.getAudioStatus())
            audio.getAudio().resumeLoop();
    }

}
