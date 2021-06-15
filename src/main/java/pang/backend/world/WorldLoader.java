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

/**
 * klasa odpowiedzialna za wczytywanie pozimów
 */
public class WorldLoader {
    /**
     * wczytany świat
     */
    private World world;
    /**
     * fabryka wrogów
     */
    private final EnemyFactory enemyFactory = new EnemyFactory();

    /**
     * tworzy worldLoadera na podstawie ścięzki, wczytując odrazu przy tym wartości z pliku o podanej ścieżce
     * @param levelPath ścieżka do poziomu
     */
    public WorldLoader(Path levelPath) {
        createWorld();
        tryLoadWorld(levelPath);
    }

    /**
     * zwraca załadowany świat
     * @return świat
     */
    public World getWorld(){
        return world;
    }

    /**
     * tworzy świat
     */
    private void createWorld() {
        GameConfig worldConfig = ConfigLoader.CONFIG_LOADER.getConfig("World");
        Player player = createPlayer();
        world = new World(worldConfig, player);
    }

    /**
     * tworzy gracza
     * @return gracz
     */
    private Player createPlayer() {
        GameConfig playerConfig = ConfigLoader.CONFIG_LOADER.getConfig("Player");
        CoolDown coolDown = getPlayerCoolDown();
        return new Player(playerConfig, coolDown);
    }

    /**
     * tworzy cooldown
     * @return cooldown
     */
    private CoolDown getPlayerCoolDown() {
        GameConfig coolDownConfig = ConfigLoader.CONFIG_LOADER.getConfig("PlayerCoolDown");
        return new CoolDown(coolDownConfig);
    }

    /**
     * próbuje wczytać świat o ścieżce podanej jako argument
     * @param path ścieżka do świata
     */
    private void tryLoadWorld(Path path){
        try{
            loadWorld(path);
        } catch (FileNotFoundException e) {
            logError(e);
        }
    }

    /**
     * wczytuje świat o ścieżce podanej jako argument
     * @param path ścieżka do świata
     * @throws FileNotFoundException wyjątek nie znalezienia pliku świata
     */
    private void loadWorld(Path path) throws FileNotFoundException {
        File file = path.toFile();
        Scanner scanner = new Scanner(file);
        loadWordFromScanner(scanner);
    }

    /**
     * wczytuje świat przy użuciu skanera
     * @param scanner wybrany do wczytania świata skaner
     */
    private void loadWordFromScanner(Scanner scanner){
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            loadDataIntoWorldLoader(line);
        }
        scanner.close();
    }

    /**
     * załodowuje pobrane z pliku dane do worldLoader'a
     * @param data dane z pliku
     */
    private void loadDataIntoWorldLoader(String data){
        String currentEnemyName = getName(data);
        Integer currentEnemyTimeSpawn = getSpawnTime(data);
        Enemy enemy = enemyFactory.createEnemyWithNameAndRespawnTime(currentEnemyName, currentEnemyTimeSpawn);
        world.addEnemy(enemy);
    }

    /**
     * wyciąga nazwę przeciwnika z danych
     * @param data dane z pliku
     * @return nazwa przeciwnika
     */
    private String getName(String data){
        String[] separatedLine = data.split(" ");
        return separatedLine[0];
    }

    /**
     * wyciąga czas spawnu przeciwnika
     * @param data dane z pliku
     * @return czas spawnu przeciwnika
     */
    private Integer getSpawnTime(String data){
        String[] separatedLine = data.split(" ");
        return Integer.parseInt(separatedLine[1]);
    }

    /**
     * wypisuje błąd na konsole
     * @param e wyjątek typu FileNOtFoundException
     */
    private void logError(FileNotFoundException e){
        System.out.println("Level file not found");
        e.printStackTrace();
    }

}
