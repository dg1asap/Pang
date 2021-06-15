package pang.hardware;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * Klasa odpowiedzialna za zarządzanie muzyką
 */
public class Audio implements MusicPlayer {

    /**
     * Plik audio
     */
    static Clip sample;
    /**
     * Przechowuje moment zapauzowania muzyki
     */
    private long time = 0;
    /**
     * Ścieżka do muzyki
     */
    static private final Path path = Path.of("data","music", "spaceSample.aif");
    /**
     * Opisuje aktualny stan grania muzyki
     */
    private boolean isPlaying = false;

    /**
     * Ładuje piosenkę i zapętla ją
     */
    public Audio(){
        this.load();
        this.loop();
    }

    /**
     * Zwraca obiekt obsługujący muzykę
     * @return zwraca obiekt Audio
     */
    public Audio getAudio(){
        return this;
    }

    /**
     * Zwraca stan odtworzenia muzyki
     * @return zwraca status odtwarzania muzyki
     */
    public boolean getAudioStatus(){
        return this.getPlayingStatus();
    }

    /**
     * Zwraca ściężkę pliku audio
     * @return zwraca ścieżkę do pliku
     */
    public Path getPath(){
        return path;
    }

    /**
     * Zwraca czas, w którym muzyka została zapauzowana
     * @return czas zatrzymania odtwarzania
     */
    public long getTime(){
        return time;
    }

    /**
     * Wyłącza muzykę
     */
    public void endMusic(){
        sample.stop();
        sample.close();
    }

    /**
     * Ładuję plik audio
     */
    public void load(){
        try {
            File music = path.toFile();
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

    /**
     * Zapętla muzykę
     */
    public void loop(){
        sample.start();
        sample.loop(sample.LOOP_CONTINUOUSLY);
        setIsPlaying();
    }

    /**
     * Odtwarza muzykę 1 raz
     */
    public void play(){
        sample.start();
        setIsPlaying();
    }

    /**
     * Pauzuje muzykę
     */
    public void pause(){
        time = sample.getMicrosecondPosition();
        sample.stop();
        setNotPlaying();
    }

    /**
     * Odtwarza zatrzymaną muzykę i gra ją do końca
     */
    public void resume(){
        if(!getPlayingStatus()) {
            sample.setMicrosecondPosition(time);
            sample.start();
            setIsPlaying();
        }
    }

    /**
     * Odtwarza zatrzymaną muzykę i zapętla ją
     */
    public void resumeLoop(){
        if(!getPlayingStatus()) {
            sample.setMicrosecondPosition(time);
            sample.start();
            sample.loop(sample.LOOP_CONTINUOUSLY);
            setIsPlaying();
        }
    }

    /**
     * Zwraca aktualny stan odtwarzania muzyki
     * @return zwraca stan odtwarzacza
     */
    private boolean getPlayingStatus(){
        return isPlaying;
    }

    /**
     * Ustawia stan audio na "odtwarzany"
     */
    private void setIsPlaying(){
        isPlaying = true;
    }

    /**
     * Ustawia stan audio na "zatrzymany"
     */
    private void setNotPlaying(){
        isPlaying = false;
    }

}