package pang.gui.panel;

import com.google.common.collect.*;
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

    private List<Map.Entry<String, Integer>> sortMultiMap(Multimap<String, Integer> notSortedMultiMap){
        List<Map.Entry<String, Integer>> pointsList = new LinkedList<>(notSortedMultiMap.entries());
        Collections.sort(pointsList,Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(pointsList);

        List<Map.Entry<String, Integer>> bestPointsList = pointsList.stream().limit(5).collect(Collectors.toList());

        return bestPointsList;
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

}
