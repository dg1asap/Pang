package pang.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

public class ConfigAndConfigLoaderTest {
    static ConfigLoader configLoader;
    static GameConfig playerConfig;
    static GameConfig worldConfig;

    @BeforeAll
    static void loadConfigs(){
        Path path = Path.of("./data/test/configs.txt");
        configLoader = new ConfigLoader(path);
        playerConfig = configLoader.getConfig("Player");
        worldConfig = configLoader.getConfig("World");
    }

    @Test
    void TestGetConfigName(){
        String playerConfigName = playerConfig.getName();
        String worldConfigName = worldConfig.getName();

        assertEquals("Player", playerConfigName);
        assertEquals("World", worldConfigName);
    }

    @Test
    void TestGetAttribute(){
        double speedValue = playerConfig.getAttribute("speed");
        double healthValue = playerConfig.getAttribute("health");
        double maxSpawnValue = worldConfig.getAttribute("maxSpawnCount");

        assertEquals(2, speedValue);
        assertEquals(10, healthValue);
        assertEquals(20, maxSpawnValue);
    }
}
