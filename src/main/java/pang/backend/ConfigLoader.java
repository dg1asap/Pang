package pang.backend;

import pang.backend.exceptions.ConfigNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigLoader {
    private final ArrayList <GameConfig> configs = new ArrayList<>(100);
    private GameConfig currentConfig;

    ConfigLoader(Path path){
        loadConfigs(path);
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

    private void selectConfig(String name) throws ConfigNotFoundException{
        for(GameConfig config : configs){
            compareConfigName(config, name);
        }
    }

    private void compareConfigName(GameConfig config, String name) {
        if(config.hasName(name)){
            this.currentConfig = config;
        }
    }

    private void checkSelectedConfig(String name) throws ConfigNotFoundException{
        if(!checkIfConfigIsLoadedCorrectly(name)){
            throw new ConfigNotFoundException(name);
        }
    }

    private boolean checkIfConfigIsLoadedCorrectly(String name){
        return currentConfig.hasName(name);
    }

    private GameConfig getSelectedConfig(){
        return currentConfig;
    }

    private void errorLog(ConfigNotFoundException e){
        System.out.println(e.errorMessage());
    }




    private void loadConfigs(Path path) {
        File file = path.toFile();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (isConfigName(line)) {
                    configs.add(new GameConfig(line));
                } else {
                    String attributeName = getAttributeName(line);
                    Double attributeValue = getAttributeValue(line);
                    GameConfig config = configs.get((configs.size() - 1));
                    config.addAttribute(attributeName, attributeValue);
                    configs.set((configs.size() - 1), config);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isConfigName(String name) {
        return !name.contains("=");
    }

    private String getAttributeName(String line){
        String[] separatedLine = line.split("=");
        return separatedLine[0].trim();

    }

    private Double getAttributeValue(String line){
        String[] separatedLine = line.split("=");
        return Double.valueOf(separatedLine[1]);
    }

}