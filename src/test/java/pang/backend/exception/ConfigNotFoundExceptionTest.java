package pang.backend.exception;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigNotFoundExceptionTest {

    @ParameterizedTest
    @MethodSource("path")
    void testOfExceptionOfNotFoundConfigFile(Path path){
        ConfigNotFoundException exceptionOfNotFoundConfigFile = ConfigNotFoundException.configPath(path);
        String errorMessage = String.format("[ConfigNotFound] ConfigLoader : A config file with path %s was not found", path);
        assertEquals(errorMessage, exceptionOfNotFoundConfigFile.errorMessage());
    }

    @ParameterizedTest
    @CsvSource({"health,Player", "speed,Player", "enemy,LargeBall", "worldCapacity,World"})
    void testOfExceptionOfNotFoundConfigAttribute(String attributeName, String configName){
        ConfigNotFoundException exceptionOfNotFoundConfigAttribute = ConfigNotFoundException.noAttributeInConfig(attributeName, configName);
        String errorMessage = String.format("[ConfigNotFound] %sConfig : An attribute named %s in %sConfig was not found", configName, attributeName, configName);
        assertEquals(errorMessage, exceptionOfNotFoundConfigAttribute.errorMessage());
    }

    @ParameterizedTest
    @MethodSource("stringAndPath")
    void testOfExceptionOfNotFoundConfigName(String configName, Path path){
        ConfigNotFoundException exceptionOfNotFoundConfigName = ConfigNotFoundException.missingConfigInPath(configName, path);
        String errorMessage = String.format("[ConfigNotFound] %sConfig : A config named %s was not found in %s", configName, configName, path);
        assertEquals(errorMessage, exceptionOfNotFoundConfigName.errorMessage());
    }

    static Stream <Arguments> path(){
        return Stream.of(
                Arguments.arguments(Path.of("./src/test/config.txt")),
                Arguments.arguments(Path.of("./src/test/config.txt"))
        );
    }

    static Stream <Arguments> stringAndPath(){
        return Stream.of(
                Arguments.arguments("MegaBall", Path.of("./src/test/config.txt")),
                Arguments.arguments("World", Path.of("./src/test/data/config.txt"))
        );
    }

}
