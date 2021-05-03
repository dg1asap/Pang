package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.Movement;
import pang.backend.config.GameConfig;
import pang.frontend.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Character implements Movement {

    int ammoAmount;
    double gravityForce;
    int dx;
    int dy;

    public Player(GameConfig config){

        super(config);
        ammoAmount = (int) config.getAttribute("ammunition");
        gravityForce = config.getAttribute("gravityForce");

        this.setPosX(config.getAttribute("startPosX"));
        this.setPosY(config.getAttribute("startPosY"));
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_W){
            if(this.y == GamePanel.getGameHeight() - this.height) {
                setY(-(int) this.speed);
            }
        } //do zmiany na skok
        else if(e.getKeyCode()==KeyEvent.VK_A){
            setX(-(int)this.speed);
        }
        else if(e.getKeyCode()==KeyEvent.VK_D){
            setX((int)this.speed);
        }
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_W){
            setY((int)gravityForce);
        }
        else if(e.getKeyCode()==KeyEvent.VK_A){
            setX(0);
        }
        else if(e.getKeyCode()==KeyEvent.VK_D){
            setX(0);
        }
    }

    public void setX(int dx){
        this.dx = dx;
    }

    public void setY(int dy){
        this.dy = dy;
    }

    public void move(){
        x = x + dx;
        y = y + dy;
    }



    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x,y,(int)width ,(int)height);
    }

    void shoot(){
        ammoAmount += -1;
        //Not implemented yet
    }

    public int getAmmoAmount(){
        return ammoAmount;
    }

    public double getGravityForce(){
        return gravityForce;
    }



    @Override
    public boolean isCollision() {
        return false; //Not implemented yet
    }

    //public boolean wallCollisionHappened(){
    //    if(this.getXPosition() == 0 || this.getXPosition() == GameWindow.getXSize()){ //window size ex. (0,1000);
    //        return true;
    //   }
    //}

   // public boolean enemyCollisionHappened(){

   // }

}
