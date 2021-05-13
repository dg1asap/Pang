package pang.backend.world;

import pang.backend.Bullet;
import pang.backend.BulletController;
import pang.backend.character.PangVector;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.player.Player;
import pang.backend.character.player.PlayerReaction;
import pang.backend.config.GameConfig;
import pang.gui.PangFrame;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class World {
    private final ArrayBlockingQueue <Enemy> enemies;
    private final Player player;
    private BulletController bulletController;

    public World(GameConfig worldConfig, Player player){
        int worldCapacity = (int) worldConfig.getAttribute("worldCapacity");
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;
        bulletController = new BulletController();
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
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
        drawEnemies(g);

    }

    public void steerKey(char keyChar, double value){
        if (canPlayerSteer(keyChar, value))
            player.steerKey(keyChar, value);
        addBulletToPlayer();
    }

    public void steerTime(long time){
        bulletController.steer();
        manageEnemies(time);
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies)
            drawEnemy(enemy, g);
    }

    private void drawEnemy(Enemy enemy, Graphics g) {
        if (enemy.isSpawned())
            enemy.draw(g);
    }

    private boolean canPlayerSteer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String direction = playerReaction.fromKeyName(keyChar);
        PangVector extremePointOfMap = PangFrame.getExtremePointOfFrame();
        WorldBorder worldBorder = new WorldBorder(extremePointOfMap);
        return worldBorder.isInBorderOfWorld(player, direction, (int)value);
    }

    private void addBulletToPlayer() {
        if (player.canShoot()) {
            bulletController.addBullet(new Bullet(player.getBulletXPos(), player.getActualYPlayerPosition() - 20));
        }
    }

    private void manageEnemies(long time) {
        for (Enemy enemy : enemies) {
            spawnEnemy(enemy, time);
            if(time % 3000 == 0) {
                moveEnemy(enemy);
            }

            killEnemy(enemy);
        }
    }

    private void spawnEnemy(Enemy enemy, long time) {
        enemy.spawn(time);
    }

    private void moveEnemy(Enemy enemy) {
        if (enemy.isSpawned())
            enemy.move();
    }

    private void killEnemy(Enemy enemy) {
        if (!enemy.isAlive())
            enemies.remove(enemy);
    }

}
