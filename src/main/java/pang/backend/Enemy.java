package pang.backend;

public abstract class Enemy {
    protected int spawnTime;

    Enemy(int spawnTime){
        this.spawnTime = spawnTime;
    }
}
