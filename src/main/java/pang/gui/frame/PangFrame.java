package pang.gui.frame;

import pang.backend.util.PangVector;
import pang.gui.panel.PangPanel;
import pang.hardware.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PangFrame extends JFrame {
    private PangVector extremePointOfFrame;

    public PangVector getFrameSize() {
        return extremePointOfFrame;
    }

    public PangFrame(Screen screen) {
        setPreferences();
        setSizeProperties(screen);
        setBasicOperations();
    }

    private void setPreferences() {
        setColour();
        setTitle("Pang");
    }

    private void setSizeProperties(Screen screen) {
        setResizeResponses(screen);
        setScreenResolution();
        setResizable(true);
        setPreferredSize(new Dimension(extremePointOfFrame.getX(), extremePointOfFrame.getY()));
        setMinimumSize(new Dimension(extremePointOfFrame.getX(), extremePointOfFrame.getY()));
        setMaximumSize(getMaxScreenSize());
    }

    private void setResizeResponses(Screen screen) {
        extremePointOfFrame = new PangVector(getWidth(), getHeight());
        addComponentListener(new ResizeAdapter(screen));

    }

    private void setBasicOperations() {
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
        setSize(width, height);
        extremePointOfFrame = new PangVector(width, height);
    }

    private Dimension getMaxScreenSize(){
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

    class ResizeAdapter extends ComponentAdapter {
        private final Screen screen;

        ResizeAdapter(Screen screen) {
            super();
            this.screen = screen;
        }

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
