package pang.backend;

import java.util.concurrent.ArrayBlockingQueue;

public class World {
    private final ArrayBlockingQueue <Enemy> enemies;
    private final Player player;

    World(GameConfig worldConfig, Player player){
        int worldCapacity = (int) worldConfig.getAttribute("worldCapacity");
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;
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

}
