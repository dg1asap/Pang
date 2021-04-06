package pang.hardware;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Audio implements MusicPlayer {
    public void play(String music) {
        try {
            File musicPath = new File(music);

            AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
            Clip sample = AudioSystem.getClip();
            sample.open(audio);
            sample.start();
            sample.loop(sample.LOOP_CONTINUOUSLY); // muzyka się zapętla non-stop

            JOptionPane.showMessageDialog(null, "Space music"); //GUI - odtworzenie audio
        } catch (FileNotFoundException notFound) {
            System.out.println("File path not found!");
            notFound.printStackTrace();
        } catch (UnsupportedAudioFileException wrongExtension) {
            System.out.println("Wrong music file extension!");
            wrongExtension.printStackTrace();
        } catch (Exception cantPlay) {
            System.out.println("Can't load audio!");
            cantPlay.printStackTrace();
        }

    }

    public void pause() {

    }
}