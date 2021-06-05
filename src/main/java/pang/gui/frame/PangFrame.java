package pang.gui.frame;

import pang.backend.util.PangVector;
import pang.gui.panel.PangPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PangFrame extends JFrame {
    private static PangVector extremePointOfFrame;

    public static PangVector getExtremePointOfFrame() {
        return extremePointOfFrame;
    }

    public void setSize(int width, int height) {
        extremePointOfFrame = new PangVector(width, height - 29);
    }

    public PangFrame() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int width = getWidth();
                int height = getHeight() - 29;
                System.out.println(width);
                extremePointOfFrame = new PangVector(width, height);
            }
        });

        setColour();
        setTitle("Pang");
        setScreenResolution();
        setResizable(true);
        setPreferredSize(new Dimension(extremePointOfFrame.getY(), extremePointOfFrame.getX()));
        //mainWindow.setResizable(false);

        setMinimumSize(new Dimension(extremePointOfFrame.getY(), extremePointOfFrame.getX()));
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
        setSize(height, width); //TODO odwrotne parametry, powinny być wczytywane z configa
    }

    private Dimension getMaxScreenSize(){
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

}
