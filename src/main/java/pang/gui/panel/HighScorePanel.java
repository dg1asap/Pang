package pang.gui.panel;

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
    private JLabel scoresLabel;
    private JLabel highScoreText;

    public HighScorePanel(Screen screen) {
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        highScoreText = new JLabel();
        scoresLabel = new JLabel();

        JLabel chooseMap = new JLabel("Choose Map to see best players:");
        add(chooseMap);
        loadHighScoreButtons();
        add(highScoreText);

        add(scoresLabel);

        add(backButton);
    }

    private static HashMap<String, Integer> sortHashMapByValueToGet5BestScores (HashMap<String, Integer> notSortedHashMap){
        List<Map.Entry<String, Integer>> pointsList = new LinkedList<>(notSortedHashMap.entrySet());
        Collections.sort(pointsList, Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(pointsList);

        List<Map.Entry<String, Integer>> bestPointsList = pointsList.stream().limit(5).collect(Collectors.toList());
        HashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> element : bestPointsList) {
            sortedHashMap.put(element.getKey(), element.getValue());
        }
        return sortedHashMap;
    }

    private void loadHighScoreButtons(){
        loadLevelNumbers();
        addHighScoresAsButtons();
    }

    private void loadLevelNumbers() {
        File highScores = Path.of("data","main", "highScores").toFile();
        levelNumbers = new ArrayList<>(Arrays.asList(highScores.listFiles()));
        Collections.sort(levelNumbers);
    }

    private void addHighScoresAsButtons() {
        for(File levelNumber : levelNumbers) {
            JButton levelButton = createHighScoreButton(levelNumber);
            add(levelButton);
        }
    }

    private JButton createHighScoreButton(File levelNumber) {
        String levelName = levelNumber.getName();
        String nameOfLevelButton = getNameOfHighScoreButton(levelName);
        JButton levelButton = new JButton(nameOfLevelButton);
        levelButton.addActionListener(e -> loadHighScores(levelName));
        return levelButton;
    }

    private String getNameOfHighScoreButton(String levelName) {
        return "Map " + levelName.substring(0, levelName.lastIndexOf("."));
    }

    private void loadHighScores(String mapName){
        File highScores = Path.of("data","main", "highScores", mapName).toFile();
        try{
            Scanner scanner = new Scanner(highScores);
            highScoreText.setText("Best players on " + getNameOfHighScoreButton(mapName) + ":");
            HashMap<String, Integer> listOfScores = new HashMap<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String name = line.substring(0, line.lastIndexOf(" ")).trim();
                String point = line.substring(line.lastIndexOf(" ")).trim();
                Integer points = Integer.parseInt(point);
                listOfScores.put(name,points);
            }

            sortHashMapByValueToGet5BestScores(listOfScores);
            scoresLabel.setText(sortHashMapByValueToGet5BestScores(listOfScores).toString());

            scanner.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

}
