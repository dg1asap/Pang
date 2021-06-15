package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Klasa odpowiedzialna za zapisywanie do plików serwera danych pobranych od klienta
 */
public class DataSaver {
    /**
     * Numer poziomu
     */
    private String levelName;
    /**
     * Wynik
     */
    private Double score;
    /**
     * Nick użytkownika
     */
    private String nick;

    /**
     * Pobiera nazwę mapy nick gracza i jego wynik
     * @param levelName numer poziomu
     * @param score wynik gracza
     * @param nick nick gracza
     */
    public DataSaver(String levelName, Double score, String nick) {
        this.levelName = levelName;
        this.score = score;
        this.nick = nick;
    }

    /**
     * Zapisuje nick gracza i jego wynik do listy najlepszych wyników dla mapy, na której grał
     */
    public void save() {
        File highScoresFile = Path.of("./ServerData", "highScores", levelName + ".txt").toFile();
        try{
            if(highScoresFile.exists()){
                FileWriter scoreWriter = new FileWriter(highScoresFile, true);
                scoreWriter.write("\n" + nick + " " + score.intValue());
                scoreWriter.close();
                System.out.println("Added score to file: " + levelName + ".txt");
            }
            else{
                FileWriter scoreWriter = new FileWriter(highScoresFile, true);
                scoreWriter.write(nick + " " + score.intValue());
                scoreWriter.close();
                System.out.println("Created new HighScores file: " + levelName + ".txt");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
