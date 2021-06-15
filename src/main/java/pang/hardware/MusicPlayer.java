package pang.hardware;

/**
 * Interfejs zarządzający muzyką
 */
public interface MusicPlayer {
    /**
     * Wyłącza muzykę
     */
    void endMusic();

    /**
     * Ładuję muzykę
     */
    void load();

    /**
     * Zapętla muzykę
     */
    void loop();

    /**
     * Odtwarza muzykę 1 raz
     */
    void play();

    /**
     * Pauzuje muzykę
     */
    void pause();

    /**
     * Odtwarza zatrzymaną muzykę i gra ją do końca
     */
    void resume();

    /**
     * Odtwarza zatrzymaną muzykę i zapętla ją
     */
    void resumeLoop();
}
