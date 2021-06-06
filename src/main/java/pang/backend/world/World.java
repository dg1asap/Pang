package pang.backend.world;

import pang.backend.bullet.Bullet;
import pang.backend.bullet.BulletController;
import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.backend.util.PangObserver;
import pang.backend.util.PangVector;
import pang.backend.character.enemy.Ball;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.player.Player;
import pang.backend.character.player.PlayerReaction;
import pang.backend.properties.config.GameConfig;
import pang.gui.frame.PangFrame;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class World implements Info, PangObserver {
    private final ArrayBlockingQueue <Enemy> enemies;
    private final Player player;
    private final BulletController playerBulletController;
    private WorldBorder worldBorder;

    public World(GameConfig worldConfig, Player player) {
        int worldCapacity = worldConfig.getAttribute("worldCapacity").intValue();
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;
        this.playerBulletController = new BulletController(player);
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public boolean isEmpty(){
        return enemies.isEmpty();
    }

    public boolean isGameOver(){
        return player.isDead();
    }

    public void draw(Graphics g) {
        player.draw(g);
        playerBulletController.draw(g);
        drawEnemies(g);
    }

    public void steerKey(char keyChar, double value){
        if(keyChar != 'w') {
            if (canPlayerSteer(keyChar, value)){
                player.steerKey(keyChar, value);
                addBulletToPlayer();
            }
        }
        else{
            if (canPlayerSteer(keyChar, value) && player.canPlayerJump()){
                player.steerKey('w', value);
            }
        }
    }

    public void steerTime(long time){
        managePlayer();
        manageEnemies(time);
    }

    @Override
    public GameInfo getGameInfo() {
        WorldInfoFactory infoFactory = new WorldInfoFactory();
        updateWorldInfoFactory(infoFactory);
        return infoFactory.create(this);
    }

    @Override
    public void pangUpdate() {
        PangVector extremePointOfMap = PangFrame.getExtremePointOfFrame();
        worldBorder = new WorldBorder(extremePointOfMap);
        player.pangUpdate();
    }

    private void updateWorldInfoFactory(WorldInfoFactory infoFactory) {
        if (needPlayerInfo()) {
            GameInfo playerInfo = player.getGameInfo();
            infoFactory.update(playerInfo);
        }
    }

    private boolean needPlayerInfo() {
        return isGameOver() || isEmpty();
    }

    private void managePlayer() {
        playerBulletController.steer();
        player.steerTime();
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
        return worldBorder.isInBorderOfWorld(player, direction, (int)value);
    }

    private void addBulletToPlayer() {
        if (player.canShoot()) {
            playerBulletController.addBullet(new Bullet(player.getBulletXPos(), player.getActualYPlayerPosition() - 20));
        }
    }

    private void manageEnemies(long time) {
        for (Enemy enemy : enemies) {
            spawnEnemy(enemy, time);
            attackEnemy(enemy);
            moveEnemy(enemy);
            killEnemy(enemy);
        }
    }

    private void spawnEnemy(Enemy enemy, long time) {
        enemy.spawn(time);
    }

    private void attackEnemy(Enemy enemy) {
        enemyAttackPlayer(enemy);
        playerAttackEnemy(enemy);
    }

    private void enemyAttackPlayer(Enemy enemy) {
        if (player.intersects(enemy))
            enemy.attack(player);
    }

    private void playerAttackEnemy(Enemy enemy) {
        playerBulletController.interact(enemy);
    }

    private void moveEnemy(Enemy enemy) {
        if (enemy.isSpawned()) {
            bounceOffBall(enemy);
            enemy.move();
        }
    }

    private void killEnemy(Enemy enemy) {
        if (enemy.isDead())
            enemies.remove(enemy);
    }

    private void bounceOffBall(Enemy enemy) {
        if (enemy instanceof Ball) {
            Ball ball = (Ball) enemy;
            bounceBallOffPlayer(ball);
        }
    }

    private void bounceBallOffPlayer(Ball ball) {
        if (player.intersects(ball)) {
            ball.bounceOff();
        }
    }


}
