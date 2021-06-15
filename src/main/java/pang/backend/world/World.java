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

/**
 * Klas reprezentująca świat gry
 */
public class World implements Info, ResizeObserver {
    /**
     * tablica przeciwników
     */
    private final ArrayBlockingQueue <Enemy> enemies;
    /**
     * gracz
     */
    private final Player player;
    /**
     * kontroler pocisków
     */
    private final BulletController playerBulletController;
    /**
     * kreator pocisków
     */
    private BulletCreator bulletCreator;
    /**
     * granice swiata
     */
    private WorldBorder worldBorder;

    /**
     * tworzy świat na podstawie konifga i gracza
     * @param worldConfig konfig
     * @param player gracz
     */
    public World(GameConfig worldConfig, Player player) {
        int worldCapacity = worldConfig.getAttribute("worldCapacity").intValue();
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;
        this.playerBulletController = new BulletController(player);
    }

    /**
     * dodaje przeciwnika do świata
     * @param enemy przeciwnik
     */
    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    /**
     * sprawdza czy świat jest pusty
     * @return zwraca wynik sprawdzenia czy świat jest pusty
     */
    public boolean isEmpty(){
        return enemies.isEmpty();
    }

    /**
     * sprawdza czy nastąpił koniec gry
     * @return zwraca wynik sprawdza czy nastąpił koniec gry
     */
    public boolean isGameOver(){
        return player.isDead();
    }

    /**
     * rysuje obiekty na świecie
     * @param g obiekty typu Graphics
     */
    public void draw(Graphics g) {
        player.draw(g);
        playerBulletController.draw(g);
        drawEnemies(g);
    }

    /**
     * sterowanie światem przy pomocy klawiatury
     * @param keyChar klawisz
     * @param value wartość
     */
    public void steerKey(char keyChar, double value){
        if(keyChar == 'w')
            jumpPlayer(value);
        else
            attackOrMovePlayer(keyChar, value);
    }

    /**
     * sterowanie światem przy pomocy czasu
     * @param time czas gry
     */
    public void steerTime(long time){
        managePlayer();
        manageEnemies(time);
    }

    /**
     * zwraca obiekt typu GameInfo
     * @return obiekt typu GameInfo
     */
    @Override
    public GameInfo getGameInfo() {
        WorldInfoFactory infoFactory = new WorldInfoFactory();
        updateWorldInfoFactory(infoFactory);
        return infoFactory.create(this);
    }

    /**
     * inicjalizacyjna zmiana wielkości świata
     * @param size wektor zmany aktualnego rozmiaru
     */
    @Override
    public void initialResize(PangVector size) {
        worldBorder = new WorldBorder(size);
        bulletCreator = new BulletCreator(size);
        initialEnemyResize(size);
        player.initialResize(size);
    }

    /**
     * zmiana wielkości świata
     * @param size wektor zmiany aktualnego rozmiaru
     */
    @Override
    public void resize(PangVector size) {
        worldBorder = new WorldBorder(size);
        player.resize(size);
        bulletCreator.resize(size);
        playerBulletController.rescaleBullets(size);
        rescaleEnemy(size);
    }

    /**
     * rysuje przeciwników na świecie
     * @param g parametr typu Graphics
     */
    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies)
            drawEnemy(enemy, g);
    }

    /**
     * rysuje przeciwnika na świecie
     * @param enemy rysowany przeciwnik
     * @param g parametr typu Graphics
     */
    private void drawEnemy(Enemy enemy, Graphics g) {
        if (enemy.isSpawned())
            enemy.draw(g);
    }

    /**
     * gracz skacze
     * @param jumpLength wysokość na którą gracz podskoczy
     */
    private void jumpPlayer(double jumpLength) {
        if (canPlayerSteer('w', jumpLength) && player.canPlayerJump()){
            player.steerKey('w', jumpLength);
        }
    }

    /**
     * gracz atakuje lub sie rusza
     * @param keyChar wciśnięty klawisz
     * @param value wartość
     */
    private void attackOrMovePlayer(char keyChar, double value) {
        if (canPlayerSteer(keyChar, value)) {
            player.steerKey(keyChar, value);
            createBullet();
        }
    }

    /**
     * tworzy pocisk
     */
    private void createBullet() {
        if (player.canShoot()) {
            int xBulletPosition = player.getBulletXPos();
            int yBulletPosition = player.getActualYPlayerPosition() - 20;
            Bullet bullet = bulletCreator.create(xBulletPosition, yBulletPosition);
            playerBulletController.addBullet(bullet);
        }
    }

    /**
     * sprawdza czy można sterować graczem
     * @param keyChar wciśnięty klawisz
     * @param value wartość
     * @return wynik sprawdzenia czy można sterować graczem
     */
    private boolean canPlayerSteer(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String direction = playerReaction.fromKeyName(keyChar);
        return worldBorder.isInBorderOfWorld(player, direction, (int)value);
    }

    /**
     * powiększa WorldInfoFactory o kolejną fabrykę
     * @param infoFactory łączona fabryka
     */
    private void updateWorldInfoFactory(WorldInfoFactory infoFactory) {
        if (needPlayerInfo()) {
            GameInfo playerInfo = player.getGameInfo();
            infoFactory.update(playerInfo);
        }
    }

    /**
     * sprawdza czy jest wymagane playerInfo
     * @return wynik sprawdzenia czy jest wymagane playerInfo
     */
    private boolean needPlayerInfo() {
        return isGameOver() || isEmpty();
    }

    /**
     * zarządza graczem
     */
    private void managePlayer() {
        playerBulletController.steer();
        player.limitMovement(worldBorder);
        player.steerTime();
    }

    /**
     * zarządza wrogiem
     * @param time czas wymagany do spawnu wrogów
     */
    private void manageEnemies(long time) {
        for (Enemy enemy : enemies) {
            spawnEnemy(enemy, time);
            attackEnemy(enemy);
            moveEnemy(enemy);
            killEnemy(enemy);
        }
    }

    /** respawn wrogów
     * @param enemy wróg
     * @param time czas gry
     */
    private void spawnEnemy(Enemy enemy, long time) {
        enemy.spawn(time);
    }

    /**
     * atak wroga
     * @param enemy atakujący i obrywający wróg
     */
    private void attackEnemy(Enemy enemy) {
        enemyAttackPlayer(enemy);
        playerAttackEnemy(enemy);
    }

    /**
     * atak wrogiem gracza
     * @param enemy atakujący wróg
     */
    private void enemyAttackPlayer(Enemy enemy) {
        if (player.intersects(enemy))
            enemy.attack(player);
    }

    /**
     * atak graczem wroga
     * @param enemy atakowany wróg
     */
    private void playerAttackEnemy(Enemy enemy) {
        playerBulletController.interact(enemy);
    }

    /**
     * poruszanie się wrogiem
     * @param enemy poruszający się wróg
     */
    private void moveEnemy(Enemy enemy) {
        if (enemy.isSpawned()) {
            bounceOffBall(enemy);
            enemy.moveInsideBorder(worldBorder);
        }
    }

    /**
     * zabicie wroga
     * @param enemy zabijany wróg
     */
    private void killEnemy(Enemy enemy) {
        if (enemy.isDead())
            enemies.remove(enemy);
    }

    /**
     * odbicie się wroga typu Ball
     * @param enemy wróg rzutowany na Ball, który się odbije
     */
    private void bounceOffBall(Enemy enemy) {
        if (enemy instanceof Ball) {
            Ball ball = (Ball) enemy;
            bounceBallOffPlayer(ball);
        }
    }

    /**
     * odbice piłki od gracza
     * @param ball odbita piłka
     */
    private void bounceBallOffPlayer(Ball ball) {
        if (player.intersects(ball)) {
            ball.bounceOff();
        }
    }

    /**
     * inicjalizacyjny resize wrogów
     * @param size inicjalizacyjny wektor zmiany aktualnego rozmiaru
     */
    private void initialEnemyResize(PangVector size) {
        for (Enemy enemy : enemies) {
            enemy.initialResize(size);
        }
    }

    /**
     * resize wrogów
     * @param size wektor zmiany aktualnego rozmiaru
     */
    private void rescaleEnemy(PangVector size) {
        for (Enemy enemy : enemies)
            enemy.resize(size);
    }


}
