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

    public boolean isEmpty(){
        return enemies.isEmpty();
    }

    public Enemy spawnFirstEnemy(){
        return enemies.poll();
    }

    public boolean isGameOver(){
        return !player.isAlive();
    }

}
