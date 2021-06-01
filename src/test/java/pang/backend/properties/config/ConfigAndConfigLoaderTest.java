package pang.backend.properties.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

public class ConfigAndConfigLoaderTest {
    static GameConfig playerConfig;
    static GameConfig worldConfig;

    @BeforeAll
    static void loadConfigs() {
        Path path = Path.of("./data/test/configs/ConfigAndConfigLoaderTest.txt");
        ConfigLoader.CONFIG_LOADER.init(path);
        playerConfig = ConfigLoader.CONFIG_LOADER.getConfig("Player");
        worldConfig = ConfigLoader.CONFIG_LOADER.getConfig("World");
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
        double worldCapacity = worldConfig.getAttribute("worldCapacity");

        assertEquals(2, speedValue);
        assertEquals(10, healthValue);
        assertEquals(100, worldCapacity);
    }
}
