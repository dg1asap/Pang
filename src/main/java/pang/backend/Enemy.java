package pang.backend;

public abstract class Enemy extends Character {
    // Movement implementation need to be added //

    protected int spawnTime;

    public Enemy(GameConfig config, int spawnTime){
        this.setHealth(config.getAttribute("health"));
        this.setDamage(config.getAttribute("damage"));
        this.setSpeed(config.getAttribute("speed"));

        this.spawnTime = spawnTime;
    }

    Enemy(int spawnTime){
        this.spawnTime = spawnTime;
    }

    void takeDamage(double damage){
        //Not implemented yet
    }
    void attack(){
        //Not implemented yet
    }
}
