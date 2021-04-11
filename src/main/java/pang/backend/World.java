package pang.backend;

import pang.backend.exceptions.ConfigNotFoundException;

import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;

public class World {
    private int worldCapacity;

    private final ArrayBlockingQueue <Enemy> enemies = new ArrayBlockingQueue<>(worldCapacity);
    private Player player;

    World(GameConfig worldConfig) throws ConfigNotFoundException{
        loadWorld(worldConfig);
        loadPlayer();
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

    private void loadWorld(GameConfig config){
        worldCapacity = (int) config.getAttribute("worldCapacity");
    }

    private void loadPlayer() throws ConfigNotFoundException {
        Path configPath = Path.of("./data/main/configs.txt");
        ConfigLoader configLoader = new ConfigLoader(configPath);
        GameConfig playerConfig = configLoader.getConfig("Player");
        this.player = new Player(playerConfig);
    }

}
