package pang.backend;

import java.util.Queue;

public class World {
    Queue <Enemy> enemies;
    Player player;

    World(Player player, Queue <Enemy> enemies){
        this.enemies = enemies;
        this.player = player;
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
