package pang.backend.world;

import pang.backend.character.CoolDown;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.enemy.EnemyFactory;
import pang.backend.character.player.Player;
import pang.backend.config.ConfigLoader;
import pang.backend.config.GameConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class WorldLoader {
    private Path path;
    private World world;
    private EnemyFactory enemyFactory;
    private ConfigLoader configLoader;

    public static WorldLoader fromConfigPathAndLevelPath(Path configPath, Path levelPath) {
        return new WorldLoader(configPath, levelPath);
    }

    public World getWorld(){
        return world;
    }

    protected WorldLoader (Path configPath, Path levelPath) {
        this.configLoader = ConfigLoader.fromConfigPath(configPath);
        this.enemyFactory = EnemyFactory.fromConfigPath(configPath);
        createWorld();
        loadWorld(levelPath);
    }

    private void createWorld() {
        GameConfig worldConfig = getWorldConfig();
        Player player = createPlayer();
        world = new World(worldConfig, player);
    }

    private GameConfig getWorldConfig() {
        return configLoader.getConfig("World");
    }

    private Player createPlayer() {
        GameConfig playerConfig = getPlayerConfig();
        CoolDown coolDown = getPlayerCoolDown();
        return new Player(playerConfig, coolDown);
    }

    private GameConfig getPlayerConfig() {
        return configLoader.getConfig("Player");
    }

    private CoolDown getPlayerCoolDown() {
        GameConfig coolDownConfig = configLoader.getConfig("PlayerCoolDown");
        return new CoolDown(coolDownConfig);
    }

    private void loadWorld(Path path){
        try{
            selectWorld(path);
            loadWorld();
        } catch (FileNotFoundException e) {
            logError(e);
        }
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
        Enemy enemy = enemyFactory.createEnemyWithNameAndRespawnTime(currentEnemyName, currentEnemyTimeSpawn);
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
