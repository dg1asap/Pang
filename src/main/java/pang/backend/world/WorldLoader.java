package pang.backend.world;

import pang.backend.character.CoolDown;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.enemy.EnemyFactory;
import pang.backend.character.player.Player;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class WorldLoader {
    private World world;
    private final EnemyFactory enemyFactory = new EnemyFactory();

    public WorldLoader(Path levelPath) {
        createWorld();
        tryLoadWorld(levelPath);
    }

    public World getWorld(){
        return world;
    }

    private void createWorld() {
        GameConfig worldConfig = ConfigLoader.CONFIG_LOADER.getConfig("World");
        Player player = createPlayer();
        world = new World(worldConfig, player);
    }

    private Player createPlayer() {
        GameConfig playerConfig = ConfigLoader.CONFIG_LOADER.getConfig("Player");
        CoolDown coolDown = getPlayerCoolDown();
        return new Player(playerConfig, coolDown);
    }

    private CoolDown getPlayerCoolDown() {
        GameConfig coolDownConfig = ConfigLoader.CONFIG_LOADER.getConfig("PlayerCoolDown");
        return new CoolDown(coolDownConfig);
    }

    private void tryLoadWorld(Path path){
        try{
            loadWorld(path);
        } catch (FileNotFoundException e) {
            logError(e);
        }
    }

    private void loadWorld(Path path) throws FileNotFoundException {
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
