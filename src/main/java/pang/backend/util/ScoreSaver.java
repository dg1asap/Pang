package pang.backend.util;

import pang.gui.panel.UserDataPanel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Klasa zapisująca wyniki
 */
public class ScoreSaver {
    /**
     * nazwa poziomu
     */
    private final String levelName;
    /**
     * uzyskany wynik
     */
    private final Double score;

    /**
     * tworzy ScoreSavera na podstawie nazwy poziomu i wyniku
     * @param levelName nazwa poziomu
     * @param score uzyskany wynik
     */
    public ScoreSaver(String levelName, Double score) {
        this.levelName = levelName;
        this.score = score;
    }

    /**
     * zapis wyników
     */
    public void save() {
        File highScoresFile = Path.of("data","main", "highScores", levelName + ".txt").toFile();
        try{
            saveHighScores(highScoresFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * zapis wyników do pliku
     * @param highScoresFile zapisywany plik
     * @throws IOException obsługa wyjątków wejścia-wyjścia
     */
    private void saveHighScores(File highScoresFile) throws IOException{
        FileWriter scoreWriter = new FileWriter(highScoresFile, true);
        if(highScoresFile.exists())
            scoreWriter.write("\n" + UserDataPanel.getUserName() + " " + score.intValue());
        else
            scoreWriter.write(UserDataPanel.getUserName() + " " + score.intValue());
        scoreWriter.close();
    }


}
