package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserDataPanel extends PangPanel{
    private ArrayList <File> levelNumbers;

    public UserDataPanel(Screen screen) {
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        JTextField userNickname = new JTextField();
        userNickname.setPreferredSize(new Dimension(200,30));
        userNickname.setMaximumSize(new Dimension(200,30));

        JLabel inputDataText= new JLabel("Please input your nick:");
        JLabel chooseLevel = new JLabel("Choose level:");
        JButton cancelButton = createButtonToChangeWindowTo("Cancel","Menu", screen);

        add(inputDataText);
        add(userNickname);
        add(chooseLevel);

        loadLevelButtons(screen);

        add(cancelButton);

    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

    private void loadLevelButtons(Screen screen){
        loadLevelNumbers();
        addLevelNumbersAsButtons(screen);
    }

    private void loadLevelNumbers() {
        File levels = Path.of("data","main", "level").toFile();
        levelNumbers = new ArrayList<>(Arrays.asList(levels.listFiles()));
        Collections.sort(levelNumbers);
    }

    private void addLevelNumbersAsButtons(Screen screen) {
        for(File levelNumber : levelNumbers) {
            JButton levelButton = createLevelButton(levelNumber, screen);
            add(levelButton);
        }
    }

    private JButton createLevelButton(File levelNumber, Screen screen) {
        String levelName = levelNumber.getName();
        String nameOfLevelButton = getNameOfLevelButton(levelName);
        JButton levelButton = createButtonToChangeWindowTo(nameOfLevelButton, "Gameplay", screen);
        levelButton.addActionListener(e -> GameplayPanel.setLevelPath(Path.of("data","main", "level", levelName)));
        return levelButton;
    }

    private String getNameOfLevelButton(String levelName) {
        return "Level " + levelName.substring(0, levelName.lastIndexOf("."));
    }

}


