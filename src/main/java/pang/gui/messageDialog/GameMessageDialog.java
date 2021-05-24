package pang.gui.messageDialog;

import pang.backend.properties.info.GameInfo;

import javax.swing.*;

public class GameMessageDialog {
    public void showMessageDialog(GameInfo gameInfo) {
        switch (gameInfo.getName()) {
            case "World" -> showWorldMessageDialog(gameInfo);
        }
    }

    private void showWorldMessageDialog(GameInfo gameInfo) {
        if (gameInfo.hasAttribute("ending")) {
            String ending = gameInfo.getAttribute("ending");
            System.out.println(ending);
            if (ending.equals("win")) {
                JOptionPane.showMessageDialog(null,"Press Ok to return to menu", "Congratulations! YOU WON", JOptionPane.PLAIN_MESSAGE);
            }

            if (ending.equals("lose")) {
                JOptionPane.showMessageDialog(null,"Press Ok to return to menu","GAME OVER", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }


}