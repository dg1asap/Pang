package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class DataSaver {
    private String levelName;
    private Double score;
    private String nick;

    public DataSaver(String levelName, Double score, String nick) {
        this.levelName = levelName;
        this.score = score;
        this.nick = nick;
    }

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
