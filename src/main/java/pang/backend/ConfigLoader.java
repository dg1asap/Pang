package pang.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigLoader {
    private ArrayList<GameConfig> configs = new ArrayList<>(20);

    ConfigLoader(Path path){
        loadConfigs(path);
    }

    public GameConfig getConfig(String name){
        for(GameConfig config : configs){
            if(config.getName().equals(name)){
                System.out.println("a");
                return config;
            }
        }
        return new GameConfig("XD");
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