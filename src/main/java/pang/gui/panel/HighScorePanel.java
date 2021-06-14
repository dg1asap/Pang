package pang.gui.panel;

import com.google.common.collect.*;
import pang.backend.properties.info.GameInfo;
import pang.hardware.Screen;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HighScorePanel extends PangPanel {
    private ArrayList <File> levelNumbers;
    private final JLabel scoresLabel = new JLabel();
    private final JLabel highScoreText = new JLabel();
    private final JComboBox<String> levelBox = new JComboBox<>();

    public HighScorePanel(Screen screen) {
        super("HighScore");
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        addChooseMapLabel();
        addLevelBox();
        addRankingLabel();
        addButtons(screen);
    }

    private void addChooseMapLabel() {
        JLabel chooseMap = new JLabel("Choose Map to see best players:");
        add(chooseMap);
    }

    private void addLevelBox() {
        loadHighScoreBox();
        loadHighScores(levelBox.getItemAt(levelBox.getSelectedIndex())  + ".txt");
        levelBox.addActionListener(e -> loadHighScores(levelBox.getItemAt(levelBox.getSelectedIndex())  + ".txt"));
    }

    private void addRankingLabel() {
        add(highScoreText);
        add(scoresLabel);
    }

    private void addButtons(Screen screen) {
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        add(backButton);
    }

    private List<Map.Entry<String, Integer>> sortMultiMap(Multimap<String, Integer> notSortedMultiMap){
        List<Map.Entry<String, Integer>> pointsList = new LinkedList<>(notSortedMultiMap.entries());
        pointsList.sort(Map.Entry.comparingByValue());
        Collections.reverse(pointsList);

        return pointsList.stream().limit(5).collect(Collectors.toList());
    }

    private void loadHighScoreBox(){
        loadLevelNumbers();
        addHighScoresToBox();
    }

    private void loadLevelNumbers() {
        File highScores = Path.of("data","main", "highScores").toFile();
        levelNumbers = new ArrayList<>(Arrays.asList(Objects.requireNonNull(highScores.listFiles())));
        Collections.sort(levelNumbers);
    }

    private void addHighScoresToBox() {
        for(File levelNumber : levelNumbers) {
            String levelName = levelNumber.getName();
            String nameOfLevel = getNameOfHighScores(levelName);
            levelBox.addItem(nameOfLevel);

        }
        add(levelBox);
    }

    private String getNameOfHighScores(String levelName) {
        return levelName.substring(0, levelName.lastIndexOf("."));
    }

    private void loadHighScores(String mapName){
        File highScores = Path.of("data","main", "highScores", mapName).toFile();
        try{
            Scanner scanner = new Scanner(highScores);
            highScoreText.setText("Best players on map " + getNameOfHighScores(mapName) + ":");
            Multimap<String, Integer> listOfScores = ArrayListMultimap.create();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String name = line.substring(0, line.lastIndexOf(" ")).trim();
                String point = line.substring(line.lastIndexOf(" ")).trim();
                Integer points = Integer.parseInt(point);
                listOfScores.put(name,points);
            }

            scoresLabel.setText(sortMultiMap(listOfScores).toString());

            scanner.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("HighScore");
    }
}
