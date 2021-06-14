package pang.backend.world;

import pang.backend.bullet.Bullet;
import pang.backend.bullet.BulletController;
import pang.backend.bullet.BulletCreator;
import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.backend.util.ResizeObserver;
import pang.backend.util.PangVector;
import pang.backend.character.enemy.Ball;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.player.Player;
import pang.backend.character.player.PlayerReaction;
import pang.backend.properties.config.GameConfig;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class World implements Info, ResizeObserver {
    private final ArrayBlockingQueue <Enemy> enemies;
    private final Player player;
    private final BulletController playerBulletController;
    private BulletCreator bulletCreator;
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
        if(keyChar == 'w')
            jumpPlayer(value);
        else
            attackOrMovePlayer(keyChar, value);
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
    public void initialResize(PangVector size) {
        worldBorder = new WorldBorder(size);
        bulletCreator = new BulletCreator(size);
        initialEnemyResize(size);
        player.initialResize(size);
    }

    @Override
    public void resize(PangVector size) {
        worldBorder = new WorldBorder(size);
        player.resize(size);
        bulletCreator.resize(size);
        playerBulletController.rescaleBullets(size);
        rescaleEnemy(size);
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies)
            drawEnemy(enemy, g);
    }

    private void drawEnemy(Enemy enemy, Graphics g) {
        if (enemy.isSpawned())
            enemy.draw(g);
    }

    private void jumpPlayer(double jumpLength) {
        if (canPlayerSteer('w', jumpLength) && player.canPlayerJump()){
            player.steerKey('w', jumpLength);
        }
    }

    private void attackOrMovePlayer(char keyChar, double value) {
        if (canPlayerSteer(keyChar, value)) {
            player.steerKey(keyChar, value);
            createBullet();
        }
    }

    private void createBullet() {
        if (player.canShoot()) {
            int xBulletPosition = player.getBulletXPos();
            int yBulletPosition = player.getActualYPlayerPosition() - 20;
            Bullet bullet = bulletCreator.create(xBulletPosition, yBulletPosition);
            playerBulletController.addBullet(bullet);
        }
    }

    private boolean canPlayerSteer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String direction = playerReaction.fromKeyName(keyChar);
        return worldBorder.isInBorderOfWorld(player, direction, (int)value);
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
        player.limitMovement(worldBorder);
        player.steerTime();
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
            enemy.moveInsideBorder(worldBorder);
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

    private void initialEnemyResize(PangVector size) {
        for (Enemy enemy : enemies) {
            enemy.initialResize(size);
        }
    }

    private void rescaleEnemy(PangVector size) {
        for (Enemy enemy : enemies)
            enemy.resize(size);
    }


}
