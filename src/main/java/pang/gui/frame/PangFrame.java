package pang.gui.frame;

import pang.backend.util.PangVector;
import pang.gui.panel.PangPanel;

import javax.swing.*;
import java.awt.*;

public class PangFrame extends JFrame {
    private static int preferredGameWidth;
    private static int preferredGameHeight;

    public static PangVector getExtremePointOfFrame() {
        return new PangVector(preferredGameWidth,preferredGameHeight);
    }

    public static void setActualScreenWidth(int width){
        preferredGameWidth = width;
    }
    public static void setActualScreenHeight(int height){
        preferredGameHeight = height;
    }

    public static int getActualScreenWidth(){
        return preferredGameWidth;
    }
    public static int getActualScreenHeight(){
        return preferredGameHeight;
    }

    public PangFrame() {
        setColour();
        setTitle("Pang");
        setScreenResolution();
        setResizable(true);
        setPreferredSize(new Dimension(preferredGameWidth, preferredGameHeight));
        //mainWindow.setResizable(false);

        setMinimumSize(new Dimension(preferredGameWidth, preferredGameHeight));
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
        preferredGameWidth = (int)size.getWidth()/2;
        preferredGameHeight = (int)size.getHeight()/2;
    }

    private Dimension getMaxScreenSize(){
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

}
