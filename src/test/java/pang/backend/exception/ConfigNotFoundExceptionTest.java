package pang.backend.exception;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigNotFoundExceptionTest {
    @ParameterizedTest
    @ValueSource(strings = {"Player", "LargeBall", "World"})
    void testOfExceptionOfNotFoundConfigName(String name){
        ConfigNotFoundException exceptionOfNotFoundConfigName = ConfigNotFoundException.configNamed(name);
        String errorMessage = String.format("[ConfigNotFound] ConfigLoader : A config named %s was not found", name);
        assertEquals(errorMessage, exceptionOfNotFoundConfigName.errorMessage());
    }

    @ParameterizedTest
    @CsvSource({"health,Player", "speed,Player", "enemy,LargeBall", "worldCapacity,World"})
    void testOfExceptionOfNotFoundConfigAttribute(String attributeName, String configName){
        ConfigNotFoundException exceptionOfNotFoundConfigAttribute = ConfigNotFoundException.noAttributeInConfig(attributeName, configName);
        String errorMessage = String.format("[ConfigNotFound] %sConfig : An attribute named %s in %sConfig was not found", configName, attributeName, configName);
        assertEquals(errorMessage, exceptionOfNotFoundConfigAttribute.errorMessage());
    }

    @ParameterizedTest
    @CsvSource({"Player,./src/test/config.txt", "smallBall,./src/test/data/config.txt"})
    void testOfExceptionOfNotFoundConfigFile(String configName, String path){
        ConfigNotFoundException exceptionOfNotFoundConfigFile = ConfigNotFoundException.missingConfigInPath(configName, path);
        String errorMessage = String.format("[ConfigNotFound] %sConfig : A config named %s was not found in %s", configName, configName, path);
        assertEquals(errorMessage, exceptionOfNotFoundConfigFile.errorMessage());
    }
}
