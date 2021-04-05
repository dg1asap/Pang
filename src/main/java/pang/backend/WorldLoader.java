package pang.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class WorldLoader {
    public static World loadLevel(int levelNumber){
        Queue <Enemy> enemies = new ArrayBlockingQueue<>(20);
        Player player = new Player();
        try{
            String path = "./data/level/" + levelNumber + ".txt";
            File file = new File(path);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] separatedLine = line.split(" ");

                if( separatedLine[0].equals("smallBall") ){
                    enemies.add( new SmallBall() );
                }

                if( separatedLine[0].equals("largeBall") ){
                    enemies.add( new LargeBall() );
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Level file not found");
            e.printStackTrace();
        }
        return new World(player ,enemies);

    }
}
