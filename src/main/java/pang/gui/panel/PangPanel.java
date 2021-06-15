package pang.gui.panel;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa abstrakcyjna, na bazie której tworzone sa inne panele
 */
public abstract class PangPanel extends JPanel implements Info {
    /**
     * Obiekt zawierający informacje o panelach
     */
    protected GameInfo panelInfo;

    /**
     * Odpowiedzialna za tworzenie nowych paneli i przypisanie ich obiektu informacyjnego
     * @param panelName nazwa panelu
     */
    public PangPanel(String panelName) {
        panelInfo = new GameInfo(panelName);
    }

    /**
     * Tworzy przycisk, który umożliwia przejście do innego panelu
     * @param buttonName nazwa przycisku
     * @param panelName nazwa panelu, na który ma się przełączyć po kliknięciu przycisku
     * @param screen menadżer zmiany panelu
     * @return zwraca JButton do przełączania paneli
     */
    public JButton createButtonToChangeWindowTo(String buttonName, String panelName, Screen screen) {
        JButton button = new JButton(buttonName);
        button.setActionCommand(panelName);
        button.addActionListener(screen);
        return button;
    }

    /**
     * Metoda odpowiedzialna za zwrócenie KeyListenerów
     * @return zwraca KeyListenery
     */
    public KeyListener getKeyListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }

    /**
     * Metoda, która nie zawiera implementacji
     * @return zwraca, informację, czy panel posiada KeyListenera
     */
    public abstract boolean hasKeyListener();
}
