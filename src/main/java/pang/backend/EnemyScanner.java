package pang.backend;

import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class EnemyScanner {
    private static Scanner scanner;
    private static ArrayBlockingQueue <String> names;
    private static ArrayBlockingQueue <Integer> spawnTimes;

    public static Iterator getNamesIterator(){
        return names.iterator();
    }

    public static Iterator getSpawnTimesIterator(){
        return  spawnTimes.iterator();
    }

    public static void loadWithScanner(Scanner scanner){
        setScanner(scanner);
        loadNamesAndSpawnTimes();
    }

    private static void setScanner(Scanner scanner){
        EnemyScanner.scanner = scanner;
    }

    private static void loadNamesAndSpawnTimes(){
        names = new ArrayBlockingQueue<>(20);
        spawnTimes = new ArrayBlockingQueue<>(20);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String name = getName(line);
            names.add(name);

            Integer spawnTime = getSpawnTime(line);
            spawnTimes.add(spawnTime);
        }
        scanner.close();
    }

    private static String getName(String line){
        String[] separatedLine = line.split(" ");
        String name = separatedLine[0];

        return name;
    }

    private static Integer getSpawnTime(String line){
        String[] separatedLine = line.split(" ");
        Integer spawnTime = Integer.parseInt(separatedLine[1]);

        return spawnTime;
    }
}
