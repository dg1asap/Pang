package pang.backend.world;

import pang.backend.Bullet;
import pang.backend.BulletController;
import pang.backend.character.PangPosition;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.player.Player;
import pang.backend.character.player.PlayerReaction;
import pang.backend.config.GameConfig;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class World {
    private final ArrayBlockingQueue <Enemy> enemies;
    private final Player player;
    private WorldBorder worldBorder;
    private BulletController bulletController;

    public World(GameConfig worldConfig, PangPosition extremePointOfMap, Player player){
        int worldCapacity = (int) worldConfig.getAttribute("worldCapacity");
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;
        this.worldBorder = new WorldBorder(extremePointOfMap);
        bulletController = new BulletController();
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

    public void draw(Graphics g) {
        player.draw(g);
        bulletController.draw(g);
    }

    public void steerKey(char keyChar, double value){
        if (canPlayerSteer(keyChar, value))
            player.steerKey(keyChar, value);
        addBulletToPlayer();
    }

    public void steerTime(){
        bulletController.steer();
    }

    private boolean canPlayerSteer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String direction = playerReaction.fromKeyName(keyChar);
        return worldBorder.isInBorderOfWorld(player,direction,(int)value);
    }

    private void addBulletToPlayer() {
        if (player.canShoot()) {
            bulletController.addBullet(new Bullet(player.getBulletXPos(), player.getActualYPlayerPosition()));
        }
    }

}
