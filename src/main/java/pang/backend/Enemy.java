package pang.backend;

public abstract class Enemy extends Character {
    // Movement implementation need to be added //

    protected int spawnTime;

    public Enemy(GameConfig config, int spawnTime){
        super(config);
        this.spawnTime = spawnTime;
    }

    void takeDamage(double damage){
        //Not implemented yet
    }
    void attack(){
        //Not implemented yet
    }
}
