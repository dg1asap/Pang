package pang.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.exception.ConfigException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigLoader {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArrayList <GameConfig> configs = new ArrayList<>(100);
    private final Path configPath;
    private GameConfig currentConfig;

    public static ConfigLoader fromConfigPath(Path configPath) {
        return new ConfigLoader(configPath);
    }

    public GameConfig getConfig(String name) {
        try {
            selectConfig(name);
            checkSelectedConfig(name);
        } catch(ConfigException e) {
            logger.error(e.errorMessage());
        }
        return getSelectedConfig();
    }

    protected ConfigLoader(Path configPath) {
        this.configPath = configPath;

        try {
            loadConfigs();
        } catch(ConfigException e) {
            logger.error(e.errorMessage());
        } catch(FileNotFoundException e) {
            logger.error("[FileNotFound] The scanner in ConfigLoader cannot open the file because it does not exist");
        }
    }

    private void loadConfigs() throws FileNotFoundException, ConfigException {
        File file = configPath.toFile();
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            loadConfigsFromScanner(scanner);
        } else {
            throw ConfigException.configPath(configPath);
        }
    }

    private void loadConfigsFromScanner(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            loadDataIntoConfigLoader(line);
        }
        scanner.close();
    }

    void loadDataIntoConfigLoader(String data) {
        if (isConfigName(data))
            addConfig(data);
        else
            addAttributeIntoLastAddedConfig(data);
    }

    private boolean isConfigName(String name) {
        return !name.contains("=");
    }

    private void addConfig(String name){
        GameConfig config = GameConfig.byName(name);
        configs.add(config);
    }

    private void addAttributeIntoLastAddedConfig(String attributes) {
        String attributeName = getAttributeName(attributes);
        Double attributeValue = getAttributeValue(attributes);

        GameConfig config = configs.get((configs.size() - 1));
        config.addAttribute(attributeName, attributeValue);
    }

    private String getAttributeName(String line) {
        String[] separatedLine = line.split("=");
        return separatedLine[0].trim();
    }

    private Double getAttributeValue(String line) {
        String[] separatedLine = line.split("=");
        return Double.valueOf(separatedLine[1]);
    }

    private void selectConfig(String name) throws ConfigException {
        for(GameConfig config : configs)
            compareConfigName(config, name);
    }

    private void compareConfigName(GameConfig config, String name) {
        if(config.hasName(name))
            this.currentConfig = config;
    }

    private void checkSelectedConfig(String name) throws ConfigException {
        if(!isCorrectlyLoadedConfig(name))
            throw ConfigException.missingConfigInPath(name, configPath);
    }

    private boolean isCorrectlyLoadedConfig(String name) {
        return currentConfig != null && currentConfig.hasName(name);
    }

    private GameConfig getSelectedConfig() {
        return currentConfig;
    }

}