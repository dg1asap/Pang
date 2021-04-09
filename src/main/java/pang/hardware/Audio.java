package pang.hardware;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;

public class Audio implements MusicPlayer {

    static Clip sample;
    long time = 0;
    static String musicPath ="./data/music/spaceSample.aif";

    String getPath(){
        return musicPath;
    }
    long getTime(){
        return time;
    }

    public void endMusic(){
        sample.stop();
        sample.close();
    }

    public void load(){
        try {
            File music = new File(musicPath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(music);
            sample = AudioSystem.getClip();
            sample.open(audio);

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

    public void loop(){
        sample.start();
        sample.loop(sample.LOOP_CONTINUOUSLY);
    }

    public void play(){
        sample.start();
    }

    public void pause(){
        time = sample.getMicrosecondPosition();
        sample.stop();
    }

    public void resume(){
        sample.setMicrosecondPosition(time);
        sample.start();
    }

    public void resumeLoop(){
        sample.setMicrosecondPosition(time);
        sample.start();
        sample.loop(sample.LOOP_CONTINUOUSLY);
    }

}