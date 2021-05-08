package pang.backend.world;

import pang.backend.character.enemy.Enemy;
import pang.backend.character.player.Player;
import pang.backend.config.GameConfig;

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

    public void steer(String keyName, double value){

    }

    protected World(GameConfig worldConfig, Player player){
        int worldCapacity = (int) worldConfig.getAttribute("worldCapacity");
        this.enemies = new ArrayBlockingQueue<>(worldCapacity);
        this.player = player;
    }

}
