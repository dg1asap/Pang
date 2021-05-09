package pang.backend.world;

import pang.backend.Bullet;
import pang.backend.BulletController;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.player.Player;
import pang.backend.config.GameConfig;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class World {
    private final ArrayBlockingQueue <Enemy> enemies;
    private final Player player;

    public static World fromWorldConfigAndPlayer(GameConfig worldConfig, Player player){
        return new World(worldConfig, player);
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public Enemy spawnEnemy(){
        return enemies.poll();
    }

    public boolean isEmpty(){
        return enemies.isEmpty();
    }

    public boolean isGameOver(){
        return !player.isAlive();
    }

    public void steer(char keyChar, double value){
        player.steer(keyChar, value);
    }

    /*
    public void checkWallCollisions(){
        if(player.getY() <= 0){
            player.getX()= 0;
        }
        if(player.y >= (gameHeight - (int)player.getHeight())){
            player.y = (gameHeight - (int)player.getHeight());
        }
        if(player.x <= 0){
            player.x = 0;
        }
        if(player.x >= (gameWidth - (int)player.getWidth())){
            player.x = (gameWidth - (int)player.getWidth());
        }
    }
    */


    public void draw(Graphics g) {
        player.draw(g);
    }

    protected World(GameConfig worldConfig, Player player){
        int worldCapacity = (int) worldConfig.getAttribute("worldCapacity");
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;

    }

}
