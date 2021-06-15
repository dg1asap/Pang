package pang.gui.panel.onlinePanels;

import pang.backend.properties.info.GameInfo;
import pang.gui.panel.PangPanel;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Panel poprzedzający okno gry w trybie online
 */
public class OnlineUserDataPanel extends PangPanel {

    /**
     * Zawiera dostępne poziomy
     */
    private ArrayList <File> levelNumbers;

    /**
     * Nick użytkownika
     */
    private static  JTextField userNickname;

    /**
     * JComboBox, w którym można wybrać dowolny poziom z dostępnych
     */
    private JComboBox<String> levelBox;

    /**
     * Zwraca nick jaki gracz podał przed przystąpieniem do rozgrywki. Jeśli gracz nie podał swojego nicku
     * metoda zwróci domyślną nazwę "Unknown"
     * @return Zwraca nick użytkownika
     */
    public static String getUserName(){
        if(userNickname.getText().trim().equals("")){
            return "Unknown";
        }
        else{
            return userNickname.getText().trim().replaceAll("\\s+"," ");
        }
    }

    /**
     * Tworzy panel, w którym użytkownik wybiera poziom na jakim chce zagrać
     * @param screen  menadżer zmiany panelu
     */
    public OnlineUserDataPanel(Screen screen) {
        super("UserData");
        userNickname = new JTextField();
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        userNickname.setPreferredSize(new Dimension(200,30));
        userNickname.setMaximumSize(new Dimension(200,30));

        JLabel inputDataText= new JLabel("Please input your nick:");
        JLabel chooseLevel = new JLabel("Choose level:");
        JButton cancelButton = createButtonToChangeWindowTo("Cancel","Online", screen);
        levelBox = new JComboBox<>();
        levelBox.addItem("Choose level: ");

        add(inputDataText);
        add(userNickname);
        add(chooseLevel);

        loadLevelBox();
        JButton levelButton = createButtonToChangeWindowTo("Play", "OnlineGameplay", screen);
        levelButton.setVisible(false);

        levelBox.addActionListener(e -> {
                    if(levelBox.getSelectedIndex() != 0){
                        GameInfo screenInfo = screen.getGameInfo();
                        String levelPath = Path.of("Downloads","maps", levelBox.getSelectedItem() + ".txt").toString();
                        screenInfo.addAttribute("levelPath", levelPath);
                        String levelName = String.valueOf(levelBox.getSelectedItem());

                        screenInfo.addAttribute("levelName", levelName);
                        levelButton.setVisible(true);
                    }
                    else{
                        levelButton.setVisible(false);
                    }
                }
        );
        add(levelButton);
        add(cancelButton);

    }

    /**
     * Ładuje ComboBox z poziomami
     */
    private void loadLevelBox(){
        loadLevelNumbers();
        addLevelsToBox();
    }

    /**
     * Wczytuje numery poziomów
     */
    private void loadLevelNumbers() {
        File levels = Path.of("Downloads","maps").toFile();
        levelNumbers = new ArrayList<>(Arrays.asList(levels.listFiles()));
        Collections.sort(levelNumbers);
    }

    /**
     * Dodaje poziomy do kontenera ComboBox
     */
    private void addLevelsToBox(){
        for(File levelNumber : levelNumbers) {
            String levelName = levelNumber.getName();
            String nameOfLevel = getNameOfLevels(levelName);
            levelBox.addItem(nameOfLevel);
        }
        add(levelBox);
    }

    /**
     * Obcina rozszerzenie pliku, w celu dostania tylko nazwy poziomu
     * @param levelName nazwa levelu
     * @return zwraca nazwę levelu bez rozszerzenia
     */
    private String getNameOfLevels(String levelName){
        return levelName.substring(0, levelName.lastIndexOf("."));
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
     * Zwraca informację z działaniem klasy OnlineUserDataPanel
     * @return zwraca obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        return new GameInfo("OnlineUserDataPanel");
    }
}


