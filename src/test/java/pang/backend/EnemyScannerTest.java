package pang.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyScannerTest {

    @BeforeEach
    void loadEnemyScanner(){
        Path path = Paths.get("./data/level/999999998.txt");
        File file = path.toFile();
        try {
            Scanner scanner = new Scanner(file);
            EnemyScanner.loadWithScanner(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetNamesIterator(){
        Iterator namesIterator = EnemyScanner.getNamesIterator();
        assertEquals(namesIterator.next(), "largeBall");
        assertEquals(namesIterator.next(), "smallBall");
    }

    @Test
    void testGetSpawnIterator(){
        Iterator spawnTimesIterator = EnemyScanner.getSpawnTimesIterator();
        assertEquals(spawnTimesIterator.next(), 999);
        assertEquals(spawnTimesIterator.next(), 998);
    }

}
