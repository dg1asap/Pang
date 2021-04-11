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

    WorldLoader (Path path) throws ConfigNotFoundException{
        createWorld();
        createEnemyFactory();
        loadWorld(path);
    }

    private void createEnemyFactory() throws ConfigNotFoundException{
        Path configPath = Path.of("./data/main/configs.txt");
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

    private void createWorld() throws ConfigNotFoundException{
        Path configPath = Path.of("./data/main/configs.txt");
        ConfigLoader worldConfigLoader = new ConfigLoader(configPath);
        GameConfig worldConfig = worldConfigLoader.getConfig("World");
        world = new World(worldConfig);
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
