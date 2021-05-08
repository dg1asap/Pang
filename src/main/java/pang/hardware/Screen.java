package pang.hardware;

import pang.gui.PanelCreator;
import pang.gui.PangPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen implements ActionListener {
    JFrame mainWindow = new JFrame();
    PanelCreator panelCreator;
    //Audio audio;
    private int preferredGameWidth;
    private int preferredGameHeight;

    private JLabel actualLevel;

    public JLabel getActualLevel(){
        return actualLevel;
    }

    public int getPreferredGameWidth(){
        return preferredGameWidth;
    }
    public int getPreferredGameHeight(){
        return preferredGameHeight;
    }

    private void makeLabels(){
        actualLevel = new JLabel("Default difficulty level: NORMAL");
    }

    public void setPanelCreator(PanelCreator panelCreator){
        this.panelCreator = panelCreator;
    }

    public Screen() {
        makeScreen();
    }

    public void makeScreen(){
        this.panelCreator = new PanelCreator(this);

        PangPanel startPanel = panelCreator.create("Menu");
        mainWindow.setTitle("Pang");
        setScreenResolution();
        mainWindow.setResizable(true);
        mainWindow.setPreferredSize(new Dimension(preferredGameWidth, preferredGameHeight));
        mainWindow.setMinimumSize(new Dimension(preferredGameWidth, preferredGameHeight));
        mainWindow.setMaximumSize(getMaxScreenSize());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setContentPane(startPanel);

        makeLabels();

        setColour();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    public void render(String panelName){
        PangPanel newPanel = panelCreator.create(panelName);
        mainWindow.setContentPane(newPanel);
        setColour();
        mainWindow.getContentPane().repaint();
        mainWindow.validate();
    }

    private void setScreenResolution(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        preferredGameWidth = (int)size.getWidth()/2;
        preferredGameHeight = (int)size.getHeight()/2;
    }

    private Dimension getMaxScreenSize(){
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    }

    private void setColour(){
        mainWindow.getContentPane().setBackground(new Color(238, 225, 127));
        mainWindow.setBackground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        render(e.getActionCommand());
    }
}
