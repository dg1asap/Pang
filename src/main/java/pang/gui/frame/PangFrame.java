package pang.gui.frame;

import pang.backend.util.PangVector;
import pang.gui.panel.PangPanel;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * główne okno gry
 */
public class PangFrame extends JFrame {
    /**
     * prawy dolny róg gry
     */
    private PangVector extremePointOfFrame;

    /**
     * zwraca prawy dolny róg okna
     * @return prawy dolny róg okna
     */
    public PangVector getFrameSize() {
        return extremePointOfFrame;
    }

    /**
     * tworzy główne okno oraz podpina je do screen'a (menadżera zmiany paneli)
     * @param screen menadżer zmiany paneli
     */
    public PangFrame(Screen screen) {
        setPreferences();
        setSizeProperties(screen);
        setBasicOperations();
    }

    /**
     * ustawia peryferie okna
     */
    private void setPreferences() {
        setColour();
        setTitle("Pang");
    }

    /**
     * ustawia właściwości okna
     * @param screen menadżer zmiany paneli
     */
    private void setSizeProperties(Screen screen) {
        setResizeResponses(screen);
        setFrameResolution();
        setResizable(true);
        setPreferredSize(new Dimension(extremePointOfFrame.getX(), extremePointOfFrame.getY()));
        setMinimumSize(new Dimension(extremePointOfFrame.getX(), extremePointOfFrame.getY()));
        setMaximumSize(getMaxScreenSize());
    }

    /**
     * wiążę okno z screenem
     * @param screen menadżer zmiany paneli
     */
    private void setResizeResponses(Screen screen) {
        extremePointOfFrame = new PangVector(getWidth(), getHeight());
        addComponentListener(new ResizeAdapter(screen));
    }

    /**
     * ustawia podstawowe operacje na oknie
     */
    private void setBasicOperations() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setLocationRelativeTo(null);
        setFocusable(true);
        setVisible(true);
    }

    /**
     * ustawia panel
     * @param newPanel ustawiany panel
     */
    public void setPanel(PangPanel newPanel) {
        setContentPane(newPanel);
        setColour();
        getContentPane().repaint();
        validate();
    }

    /**
     * ustawia kolor okna
     */
    private void setColour(){
        getContentPane().setBackground(new Color(238, 225, 127));
        setBackground(Color.red);
    }

    /**
     * ustawia rozdzielczość okna
     */
    private void setFrameResolution(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth()/2;
        int height = (int)size.getHeight()/2;
        setSize(width, height);
        extremePointOfFrame = new PangVector(width, height);
    }

    /**
     * zwraca maxymalny rozmiar okan
     * @return obiekt typu Dimension, tutaj reprezentuje maksymalny rozmiar okna
     */
    private Dimension getMaxScreenSize(){
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

    /**
     * addapter przechwytujący zdarzenie zmiany rozmiaru okna
     */
    class ResizeAdapter extends ComponentAdapter {
        /**
         * menadżer zmiany paneli
         */
        private final Screen screen;

        /**
         * tworzy adapter z agregacją screen'a
         * @param screen menadżer zmiany paneli
         */
        ResizeAdapter(Screen screen) {
            super();
            this.screen = screen;
        }

        /**
         * metoda nasłuchująca zmianę rozmiaru okna
         * @param e wydarzenie zmiany rozmiaru okna
         */
        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            int width = getWidth();
            int height = getHeight();
            extremePointOfFrame = new PangVector(width, height - 29, extremePointOfFrame.getX(), extremePointOfFrame.getY());
            screen.resizeNotify();
        }
    }

}
