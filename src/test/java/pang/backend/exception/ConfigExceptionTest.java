package pang.backend.exception;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigExceptionTest {

    @ParameterizedTest(name = "#{index} File with path {0} not found")
    @MethodSource("path")
    void testOfExceptionOfNotFoundConfigFile(Path path){
        ConfigException exceptionOfNotFoundConfigFile = ConfigException.configPath(path);
        String errorMessage = String.format("[ConfigNotFound] ConfigLoader : A config file with path %s was not found", path);
        assertEquals(errorMessage, exceptionOfNotFoundConfigFile.errorMessage());
    }

    @ParameterizedTest(name = "#{index} Missing attribute {0} in {1}Config")
    @CsvSource({"health,Player", "speed,Player", "damage,LargeBall", "worldCapacity,World"})
    void testOfExceptionOfNotFoundConfigAttribute(String attributeName, String configName){
        ConfigException exceptionOfNotFoundConfigAttribute = ConfigException.noAttributeInConfig(attributeName, configName);
        String errorMessage = String.format("[ConfigNotFound] %sConfig : An attribute named %s in %sConfig was not found", configName, attributeName, configName);
        assertEquals(errorMessage, exceptionOfNotFoundConfigAttribute.errorMessage());
    }

    @ParameterizedTest(name = "#{index} Missing {0}Config in file with path {1}")
    @MethodSource("stringAndPath")
    void testOfExceptionOfNotFoundConfigName(String configName, Path path){
        ConfigException exceptionOfNotFoundConfigName = ConfigException.missingConfigInPath(configName, path);
        String errorMessage = String.format("[ConfigNotFound] %sConfig : A config named %s was not found in %s", configName, configName, path);
        assertEquals(errorMessage, exceptionOfNotFoundConfigName.errorMessage());
    }

    static Stream <Arguments> path(){
        return Stream.of(
                Arguments.arguments(Path.of("./src/test/config.txt")),
                Arguments.arguments(Path.of("./src/test/data/config.txt"))
        );
    }

    static Stream <Arguments> stringAndPath(){
        return Stream.of(
                Arguments.arguments("MegaBall", Path.of("./src/test/config.txt")),
                Arguments.arguments("World", Path.of("./src/test/data/config.txt"))
        );
    }

}
