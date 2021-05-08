package pang.gui;

import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;

public class UserDataPanel extends PangPanel {
    public UserDataPanel(Screen screen) {
        JTextField userNickname = new JTextField();
        userNickname.setPreferredSize(new Dimension(200,50));

        JLabel inputDataText= new JLabel("Please input your nick");


        JButton okButton = createButtonToChangeWindowTo("Submit","Gameplay", screen);
        JButton cancelButton = createButtonToChangeWindowTo("Cancel","Menu", screen);

        add(inputDataText);
        add(userNickname);
        add(okButton);
        add(cancelButton);

    }

    @Override
    public boolean hasKeyListener() {
        return false;
    }

}


