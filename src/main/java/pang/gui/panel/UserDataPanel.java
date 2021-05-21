package pang.gui.panel;

import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserDataPanel extends PangPanel {
    private ArrayList <File> levelNumbers;
    private static  JTextField userNickname;
    private JComboBox<String> levelBox;

    public static String getUserName(){
        if(userNickname.getText().trim().equals("")){
            return "Unknown";
        }
        else{
            return userNickname.getText().trim().replaceAll("\\s+"," ");
        }
    }

    public UserDataPanel(Screen screen) {
        userNickname = new JTextField();
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        userNickname.setPreferredSize(new Dimension(200,30));
        userNickname.setMaximumSize(new Dimension(200,30));

        JLabel inputDataText= new JLabel("Please input your nick:");
        JLabel chooseLevel = new JLabel("Choose level:");
        JButton cancelButton = createButtonToChangeWindowTo("Cancel","Menu", screen);
        levelBox = new JComboBox<>();
        levelBox.addItem("Choose level: ");

        add(inputDataText);
        add(userNickname);
        add(chooseLevel);

        loadLevelBox();
        JButton levelButton = createButtonToChangeWindowTo("Play", "Gameplay", screen);
        levelButton.setVisible(false);

        levelBox.addActionListener(e -> {
            System.out.println(levelBox.getItemAt(levelBox.getSelectedIndex()) + ".txt");
            if(levelBox.getSelectedIndex() != 0){
                GameplayPanel.setLevelPath(Path.of("data","main", "level", levelBox.getItemAt(levelBox.getSelectedIndex()) + ".txt"));
                GameplayPanel.setMapName(levelBox.getItemAt(levelBox.getSelectedIndex()) + ".txt");
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

    private void loadLevelBox(){
        loadLevelNumbers();
        addLevelsToBox();
    }

    private void loadLevelNumbers() {
        File levels = Path.of("data","main", "level").toFile();
        levelNumbers = new ArrayList<>(Arrays.asList(levels.listFiles()));
        Collections.sort(levelNumbers);
    }

    private void addLevelsToBox(){
        for(File levelNumber : levelNumbers) {
            String levelName = levelNumber.getName();
            String nameOfLevel = getNameOfLevels(levelName);
            levelBox.addItem(nameOfLevel);
        }
        add(levelBox);
    }

    private String getNameOfLevels(String levelName){
        return levelName.substring(0, levelName.lastIndexOf("."));
    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

}


