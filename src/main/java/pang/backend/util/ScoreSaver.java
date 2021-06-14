package pang.backend.util;

import pang.gui.panel.UserDataPanel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ScoreSaver {
    private final String levelName;
    private final Double score;

    public ScoreSaver(String levelName, Double score) {
        this.levelName = levelName;
        this.score = score;
    }

    public void save() {
        File highScoresFile = Path.of("data","main", "highScores", levelName + ".txt").toFile();
        try{
            saveHighScores(highScoresFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveHighScores(File highScoresFile) throws IOException{
        FileWriter scoreWriter = new FileWriter(highScoresFile, true);
        if(highScoresFile.exists())
            scoreWriter.write("\n" + UserDataPanel.getUserName() + " " + score.intValue());
        else
            scoreWriter.write(UserDataPanel.getUserName() + " " + score.intValue());
        scoreWriter.close();
    }


}
