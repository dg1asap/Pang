package pang.backend.config;

import pang.backend.exception.ConfigNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigLoader {
    private final ArrayList <GameConfig> configs = new ArrayList<>(100);
    private GameConfig currentConfig;
    private Path configPath;

    public static ConfigLoader fromConfigPath(Path configPath){
        return new ConfigLoader(configPath);
    }

    public GameConfig getConfig(String name) throws ConfigNotFoundException{
        try {
            selectConfig(name);
            checkSelectedConfig(name);
            return getSelectedConfig();
        } catch(ConfigNotFoundException e) {
            errorLog(e);
            throw e;
        }
    }

    protected ConfigLoader(Path configPath){
        loadConfigs(configPath);
    }

    private void loadConfigs(Path configPath) {
        try {
            loadConfigsWithPath(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadConfigsWithPath(Path configPath) throws FileNotFoundException{
        this.configPath = configPath;
        File file = configPath.toFile();
        Scanner scanner = new Scanner(file);
        loadConfigsFromScanner(scanner);
    }

    private void loadConfigsFromScanner(Scanner scanner){
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            loadDataIntoConfigLoader(line);
        }
        scanner.close();
    }

    void loadDataIntoConfigLoader(String data){
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

    private void addAttributeIntoLastAddedConfig(String attributes){
        String attributeName = getAttributeName(attributes);
        Double attributeValue = getAttributeValue(attributes);

        GameConfig config = configs.get((configs.size() - 1));
        config.addAttribute(attributeName, attributeValue);
    }

    private String getAttributeName(String line){
        String[] separatedLine = line.split("=");
        return separatedLine[0].trim();

    }

    private Double getAttributeValue(String line){
        String[] separatedLine = line.split("=");
        return Double.valueOf(separatedLine[1]);
    }

    private void selectConfig(String name) throws ConfigNotFoundException{
        for(GameConfig config : configs)
            compareConfigName(config, name);
    }

    private void compareConfigName(GameConfig config, String name) {
        if(config.hasName(name))
            this.currentConfig = config;
    }

    private void checkSelectedConfig(String name) throws ConfigNotFoundException{
        if(!isCorrectlyLoadedConfig(name))
            throw ConfigNotFoundException.missingConfigInPath(currentConfig.getName(), configPath);
    }

    private boolean isCorrectlyLoadedConfig(String name){
        return currentConfig != null && currentConfig.hasName(name);
    }

    private GameConfig getSelectedConfig(){
        return currentConfig;
    }

    private void errorLog(ConfigNotFoundException e){
        System.out.println(e.errorMessage());
    }

}