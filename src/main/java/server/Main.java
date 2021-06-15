package server;

/**
 * Klasa Main Servera
 */

public class Main {
    /**
     * Uruchamia server
     * @param args ...`
     */
    public static void main(String[] args){
        new Thread(new Server(44444)).start();
    }
}
