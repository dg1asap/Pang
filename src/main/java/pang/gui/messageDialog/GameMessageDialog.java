package pang.gui.messageDialog;

import pang.backend.properties.info.GameInfo;

import javax.swing.*;

/**
 * Klasa odpowiedzialna za treść wiadomości wyświetlającej się po skończeniu gry
 */
public class GameMessageDialog {
    /**
     * Pokazuje okienko z wiadomością
     * @param gameInfo obiekt z informacjami o panelu
     */
    public void showMessageDialog(GameInfo gameInfo) {
        switch (gameInfo.getName()) {
            case "World" -> showWorldMessageDialog(gameInfo);
        }
    }

    /**
     * Wyświetla wiadomości po skończeniu gry
     * @param gameInfo obiekt z informacjami o panelu
     */
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