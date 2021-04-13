package pang.backend;

import pang.backend.exceptions.ConfigNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class WorldLoader {
    private Path path;
    private World world;
    private EnemyFactory enemyFactory;

    WorldLoader (Path configPath, Path levelPath) throws ConfigNotFoundException{
        createWorld(configPath);
        createEnemyFactory(configPath);
        loadWorld(levelPath);
    }

    private void createEnemyFactory(Path configPath) throws ConfigNotFoundException{
        enemyFactory = new EnemyFactory(configPath);
    }

    public World getWorld(){
        return world;
    }

    private void loadWorld(Path path){
        try{
            selectWorld(path);
            loadWorld();
        } catch (FileNotFoundException e) {
            logError(e);
        }
    }

    private void createWorld(Path configPath) throws ConfigNotFoundException{
        GameConfig worldConfig = getWorldConfig(configPath);
        Player player = createPlayer(configPath);
        world = new World(worldConfig, player);
    }

    private GameConfig getWorldConfig(Path configPath) throws ConfigNotFoundException{
        ConfigLoader worldConfigLoader = new ConfigLoader(configPath);
        return worldConfigLoader.getConfig("World");
    }

    private Player createPlayer(Path configPath) throws ConfigNotFoundException{
        GameConfig playerConfig = getPlayerConfig(configPath);
        return new Player(playerConfig);
    }

    private GameConfig getPlayerConfig(Path configPath) throws  ConfigNotFoundException{
        ConfigLoader playerConfigLoader = new ConfigLoader(configPath);
        return playerConfigLoader.getConfig("Player");
    }

    private void selectWorld(Path path){
        this.path = path;
    }

    private void loadWorld() throws FileNotFoundException {
        File file = path.toFile();
        Scanner scanner = new Scanner(file);
        loadWordFromScanner(scanner);
    }

    private void loadWordFromScanner(Scanner scanner){
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            loadDataIntoWorldLoader(line);
        }
        scanner.close();
    }

    private void loadDataIntoWorldLoader(String data){
        String currentEnemyName = getName(data);
        Integer currentEnemyTimeSpawn = getSpawnTime(data);
        Enemy enemy = enemyFactory.create(currentEnemyName, currentEnemyTimeSpawn);
        world.addEnemy(enemy);
    }

    private String getName(String data){
        String[] separatedLine = data.split(" ");
        return separatedLine[0];
    }

    private Integer getSpawnTime(String data){
        String[] separatedLine = data.split(" ");
        return Integer.parseInt(separatedLine[1]);
    }

    private void logError(FileNotFoundException e){
        System.out.println("Level file not found");
        e.printStackTrace();
    }

}
