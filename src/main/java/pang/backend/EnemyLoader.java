package pang.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class EnemyLoader {
    static Queue <Enemy> enemies;
    static int levelNumber;
    static Path path;

    public static Queue <Enemy> loadLevel(int levelNumber) {
        try{
            selectSource(levelNumber);
            loadSource();
        } catch (FileNotFoundException e) {
            logError(e);
        }

        return enemies;
    }

    private static void selectSource(int levelNumber){
        setLevel(levelNumber);
        updatePath();
    }

    private static void setLevel(int levelNumber){
        EnemyLoader.levelNumber = levelNumber;
    }

    private static void updatePath(){
        path = Paths.get("./data/level/" + levelNumber + ".txt");
    }

    private static void loadSource() throws FileNotFoundException {
        loadFromFile();
        loadToEnemies();
    }

    private static void loadFromFile() throws FileNotFoundException {
        File file = path.toFile();
        Scanner scanner = new Scanner(file);
        EnemyScanner.loadWithScanner(scanner);
    }

    private static void loadToEnemies() {
        enemies = new ArrayBlockingQueue<>(20);

        Iterator names = EnemyScanner.getNamesIterator();
        Iterator spawnTimes = EnemyScanner.getSpawnTimesIterator();
        while (names.hasNext() || spawnTimes.hasNext()){
            String name = (String) names.next();
            Integer spawnTime = (Integer) spawnTimes.next();
            loadEnemy(name, spawnTime);
        }
    }

    private static void loadEnemy(String name, Integer spawnTime){
        Enemy enemy = EnemySpawner.spawn(name, spawnTime);
        enemies.add(enemy);
    }

    private static void logError(FileNotFoundException e){
        System.out.println("Level file not found");
        e.printStackTrace();
    }

}


