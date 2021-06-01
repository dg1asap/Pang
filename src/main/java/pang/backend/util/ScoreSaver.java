package pang.backend.util;

import pang.gui.panel.UserDataPanel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ScoreSaver {
    private String levelName;
    private Double score;

    public ScoreSaver(String levelName, Double score) {
        this.levelName = levelName;
        this.score = score;
    }

    public void save() {
        File highScoresFile = Path.of("data","main", "highScores", levelName + ".txt").toFile();
        try{
            if(highScoresFile.exists()){
                FileWriter scoreWriter = new FileWriter(highScoresFile, true);
                scoreWriter.write("\n" + UserDataPanel.getUserName() + " " + score.intValue());
                scoreWriter.close();
                System.out.println("Added score to file: " + levelName + ".txt");
            }
            else{
                FileWriter scoreWriter = new FileWriter(highScoresFile, true);
                scoreWriter.write(UserDataPanel.getUserName() + " " + score.intValue());
                scoreWriter.close();
                System.out.println("Created new HighScores file: " + levelName + ".txt");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
