package pang.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ArrayBlockingQueue;

public class ConfigAndConfigLoaderTest {
    static ArrayBlockingQueue <PangConfig> pangConfigs;
    static PangConfig playerConfig;
    static PangConfig worldConfig;

    @BeforeAll
    static void loadConfigs(){
        pangConfigs = PangConfigLoader.getConfigs();
        playerConfig = pangConfigs.next();
        worldConfig = pangConfigs.next();
    }

    @Test
    void TestGetConfigName(){
        String playerConfigName = playerConfig.getName();
        String worldConfigName = pangConfigs.getName();

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
