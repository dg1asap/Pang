package pang.gui.frame;

import pang.backend.util.PangObservable;
import pang.backend.util.PangObserver;
import pang.backend.util.PangVector;
import pang.gui.panel.PangPanel;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PangFrame extends JFrame {
    private static PangVector extremePointOfFrame;
    private static PangVector lastExtremePointOfFrame;

    public static PangVector getExtremePointOfFrame() {
        return extremePointOfFrame;
    }

    public PangFrame(Screen screen) {
        lastExtremePointOfFrame = new PangVector(getWidth(), getHeight());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int width = getWidth();
                int height = getHeight();
                extremePointOfFrame = new PangVector(width, height - 29, lastExtremePointOfFrame.getX(), lastExtremePointOfFrame.getY());
                lastExtremePointOfFrame = extremePointOfFrame;
                screen.notifyPang();
            }
        });

        setColour();
        setTitle("Pang");
        setScreenResolution();
        setResizable(true);
        setPreferredSize(new Dimension(extremePointOfFrame.getX(), extremePointOfFrame.getY()));
        //mainWindow.setResizable(false);

        setMinimumSize(new Dimension(extremePointOfFrame.getX(), extremePointOfFrame.getY()));
        setMaximumSize(getMaxScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setLocationRelativeTo(null);
        setFocusable(true);
        setVisible(true);
    }

    public void setPanel(PangPanel newPanel) {
        setContentPane(newPanel);
        setColour();
        getContentPane().repaint();
        validate();
    }

    private void setColour(){
        getContentPane().setBackground(new Color(238, 225, 127));
        setBackground(Color.red);
    }

    private void setScreenResolution(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth()/2;
        int height = (int)size.getHeight()/2;
        setSize(width, height); //TODO odwrotne parametry, mogą być wczytywane z configa
        extremePointOfFrame = new PangVector(width, height);
    }

    private Dimension getMaxScreenSize(){
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

}
