package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.hardware.Audio;
import pang.hardware.Screen;

import javax.swing.*;

/**
 * Panel ustawień
 */
public class SettingsPanel extends PangPanel {
    /**
     * Tworzy panel ustawień
     * @param screen menadżer zmiany panelu
     * @param audio obiekt przechowujący muzykę
     */
    SettingsPanel(Screen screen, Audio audio) {
        super("Settings");
        addMusicButton(audio);
        addBackButton(screen);
    }

    /**
     * Metoda zwracająca false, ponieważ klasa nie posiada żadnego KeyListenera
     * @return zwraca false
     */
    @Override
    public boolean hasKeyListener() {
        return false;
    }

    /**
     * Zwraca informację z działaniem klasy SettingsPanel
     * @return zwraca obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("Settings");
    }

    /**
     * Dodaje przycisk odpowiedzialny za włączanie/wyłączanie odtwarzania muzyki
     * @param audio obiekt przechowujący muzykę
     */
    private void addMusicButton(Audio audio) {
        JButton musicButton = new JButton("Turn On/Off music");
        musicButton.addActionListener(e -> playPauseMusic(audio));
        add(musicButton);
    }

    /**
     * Tworzy przycisk umożliwiający przejście do menu głównego
     * @param screen menadżer zmiany panelu
     */
    private void addBackButton(Screen screen) {
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        add(backButton);
    }

    /**
     * Odtwarza / pauzuje muzykę w zależności od statusu odtwarzania
     * @param audio obiekt przechowujący muzykę
     */
    private void playPauseMusic(Audio audio) {
        if(audio.getAudioStatus())
            audio.getAudio().pause();
        else if(!audio.getAudioStatus())
            audio.getAudio().resumeLoop();
    }

}
