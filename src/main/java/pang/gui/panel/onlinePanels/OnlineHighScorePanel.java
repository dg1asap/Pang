package pang.gui.panel.onlinePanels;

import com.google.common.collect.*;
import pang.backend.properties.info.GameInfo;
import pang.gui.panel.PangPanel;
import pang.hardware.Screen;
import pang.online.Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel odpowiedzialny za wczytanie i pokazanie list najlepszych wyników, dla trybu online
 */
public class OnlineHighScorePanel extends PangPanel {
    /**
     * Zawiera dostępne listy najlepszych wyników, dla poziomów, na których ktoś wcześniej grał
     */
    private ArrayList <File> levelNumbers;

    /**
     * Przechowuje listę kilku najlepszych wyników dla danej mapy
     */
    private JLabel scoresLabel;

    /**
     * Przechowuje etykietę z nazwą mapy, dla której są wyświetlane wyniki
     */
    private JLabel highScoreText;

    /**
     * JComboBox, w którym można wybrać dowolną listę najlepszych wyników
     */
    private JComboBox<String> levelBox;

    /**
     * Tworzy panel, z listami najlepszych wyników, dla trybu online
     * @param screen menadżer zmiany panelu
     */
    public OnlineHighScorePanel(Screen screen) {
        super("HighScore");
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        JButton backButton = createButtonToChangeWindowTo("Back","Menu", screen);
        backButton.addActionListener(e -> Client.sendCommand("logout"));
        highScoreText = new JLabel();
        scoresLabel = new JLabel();

        JLabel chooseMap = new JLabel("Choose Map to see best players:");
        add(chooseMap);
        levelBox = new JComboBox<>();
        loadHighScoreBox();
        loadHighScores(levelBox.getItemAt(levelBox.getSelectedIndex())  + ".txt");
        levelBox.setPreferredSize(new Dimension(200,30));
        levelBox.setMaximumSize(new Dimension(200,30));

        levelBox.addActionListener(e -> loadHighScores(levelBox.getItemAt(levelBox.getSelectedIndex())  + ".txt"));

        add(highScoreText);
        add(scoresLabel);

        add(backButton);
    }

    /**
     * Bierze nieposortowaną mapę, sortuje ją od największych wartości do najmniejszych i zwraca posortowaną mapę
     * @param notSortedMultiMap nieposortowana MultiMap
     * @return zwraca posortowaną MultiMap
     */
    private List<Map.Entry<String, Integer>> sortMultiMap(Multimap<String, Integer> notSortedMultiMap){
        List<Map.Entry<String, Integer>> pointsList = new LinkedList<>(notSortedMultiMap.entries());
        Collections.sort(pointsList,Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(pointsList);

        List<Map.Entry<String, Integer>> bestPointsList = pointsList.stream().limit(5).collect(Collectors.toList());

        return bestPointsList;
    }


    /**
     * Ładuje JComboBox
     */
    private void loadHighScoreBox(){
        loadLevelNumbers();
        addHighScoresToBox();
    }

    /**
     * Dodaje nazwy poziomów do tablicy
     */
    private void loadLevelNumbers() {
        File highScores = Path.of("Downloads", "highScores").toFile();
        levelNumbers = new ArrayList<>(Arrays.asList(highScores.listFiles()));
        Collections.sort(levelNumbers);
    }

    /**
     * Dodaje nazwy dostępnych list z najlepszymi wynikami do JComboBox
     */
    private void addHighScoresToBox() {
        for(File levelNumber : levelNumbers) {
            String levelName = levelNumber.getName();
            String nameOfLevel = getNameOfHighScores(levelName);
            levelBox.addItem(nameOfLevel);

        }
        add(levelBox);
    }

    /**
     * Obcina rozszerzenie pliku, w celu dostania tylko nazwy poziomu
     * @param levelName nazwa poziomu
     * @return zwraca nazwę levelu bez rozszerzenia
     */
    private String getNameOfHighScores(String levelName) {
        return levelName.substring(0, levelName.lastIndexOf("."));
    }

    /**
     * Ładuje listy najlepszych wyników
     * @param mapName nazwa mapy
     */
    private void loadHighScores(String mapName){
        File highScores = Path.of("Downloads", "highScores", mapName).toFile();
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

    /**
     * Metoda zwracająca false, ponieważ klasa nie posiada żadnego KeyListenera
     * @return zwraca false
     */
    @Override
    public boolean hasKeyListener() {
        return false;
    }

    /**
     * Zwraca informację z działaniem klasy OnlineHighScorePanel
     * @return zwraca obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("HighScore");
    }
}
