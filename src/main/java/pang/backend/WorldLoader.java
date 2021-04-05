package pang.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class WorldLoader {
    public static World loadLevel(int levelNumber){
        Queue <Enemy> enemies = new ArrayBlockingQueue<Enemy>(20);
        try{
            String path = "./data/level/" + levelNumber + ".txt";
            File file = new File(path);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Level file not found");
            e.printStackTrace();
        }

        return new World(enemies);

    }
}
