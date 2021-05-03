package pang.frontend;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame {

    GamePanel game;

    public Screen(){
        game = new GamePanel();
        this.add(game);
        this.setTitle("PANG");
        ImageIcon logo = new ImageIcon("logo1.png");
        this.setIconImage(logo.getImage());
        this.setResizable(true); //TODO 1. trzeba bedzie zmienic na okno skalowalne
        this.setBackground(Color.orange);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }



}

