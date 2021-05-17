package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class UserDataPanel extends PangPanel{

    private File levels;
    private File[] levelNumber;

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

    private void loadLevelButtons(Screen screen){
        levels = Path.of("data","main","level").toFile();
        levelNumber = levels.listFiles();

        for(int i=levelNumber.length -1; i>=0 ; i--){
            JButton levelButton = createButtonToChangeWindowTo("Level "+ levelNumber[i].getName().substring(0, levelNumber[i].getName().lastIndexOf(".")),"Gameplay", screen);
            String levelName = levelNumber[i].getName();
            levelButton.addActionListener(e->GameplayPanel.setLevelPath(Path.of("data","main","level",levelName)));
            add(levelButton);
        }
    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

}


