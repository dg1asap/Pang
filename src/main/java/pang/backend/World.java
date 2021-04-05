package pang.backend;

import java.util.Queue;

public class World {
     Queue <Enemy> enemies;

    World( Queue <Enemy> enemies){
        this.enemies = enemies;
    }

    public boolean isEmpty(){
        return enemies.isEmpty();
    }

    public Enemy spawnFirstEnemy(){
        return new SmallBall();
    }

    public boolean isGameOver(){
        return false;
    }
}
