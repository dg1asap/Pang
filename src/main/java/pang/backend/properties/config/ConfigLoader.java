package pang.backend.properties.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.exception.ConfigException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * singleton, wczytujący i zwracający obiekty konfiguracji
 */
public enum ConfigLoader {
    /**
     * singleton
     */
    CONFIG_LOADER;
    /**
     * logger wypisujacy, błędy na konsole
     */
    protected final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    /**
     * lista obiektów konfiguracji
     */
    private final ArrayList <GameConfig> configs = new ArrayList<>();
    /**
     * ścieżka do pliku konfiguracji
     */
    private Path configPath;
    /**
     * aktualny obiekt konfiguracji
     */
    private GameConfig currentConfig;

    /**
     * pobranie obiektów konfiguracji z pliku o ścieżce podanej za argument
     * @param path ścieżka do pliku konfiguracji
     */
    public void init(Path path) {
        configPath = path;

        try {
            loadConfigs();
        } catch(ConfigException e) {
            logger.error(e.errorMessage());
        } catch(FileNotFoundException e) {
            logger.error("[FileNotFound] The scanner in ConfigLoader cannot open the file with path " + configPath + " because it does not exist");
        }
    }

    /**
     * zwraca konfig o nazwie podanej jako argument
     * @param name nazwa szukanego konfiga
     * @return szukany konfig
     */
    public GameConfig getConfig(String name) {
        try {
            selectConfig(name);
            checkSelectedConfig(name);
        } catch(ConfigException e) {
            logger.error(e.errorMessage());
        }
        return currentConfig;
    }

    /** pobiera przy pomocy java.scanner konfigi
     * @throws FileNotFoundException wypisuje na konsole błędy dotyczące nie znalezienia pliku
     * @throws ConfigException wypisuje na konsole błędy dotyczące bezpośrednio konfiga
     */
    private void loadConfigs() throws FileNotFoundException, ConfigException {
        File file = configPath.toFile();
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            loadConfigsFromScanner(scanner);
        } else {
            throw ConfigException.configPath(configPath);
        }
    }

    /**
     * pobiera konfig przy pomocy skanera
     * @param scanner skaner, przy pomocy którego zostaną wczytane konfigi
     */
    private void loadConfigsFromScanner(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            loadDataIntoConfigLoader(line);
        }
        scanner.close();
    }

    /**
     * ładuje pobrane z pliku dane do configLoader'a
     * @param data dane, które zostaną pobrane do configLoader'a
     */
    private void loadDataIntoConfigLoader(String data) {
        if (isConfigName(data))
            addConfig(data);
        else
            addAttributeIntoLastAddedConfig(data);
    }

    /**
     * sprawdza czy podany za argument string jest nazwą obiektu konfiguracji
     * @param name sprawdzana nazwa konifgu
     * @return wynik sprawdzenia poprawności nazwy konfiga
     */
    private boolean isConfigName(String name) {
        return !name.contains("=");
    }

    /**
     * dodaje konfig do ConfigLoader'a
     * @param name nazwa konfiga
     */
    private void addConfig(String name){
        GameConfig config = new GameConfig(name);
        configs.add(config);
    }

    /**
     * dodaje atrybut do ostatnio dodanego konifga
     * @param attributes dodawany atrybut
     */
    private void addAttributeIntoLastAddedConfig(String attributes) {
        String attributeName = getAttributeName(attributes);
        Double attributeValue = getAttributeValue(attributes);

        GameConfig config = configs.get((configs.size() - 1));
        config.addAttribute(attributeName, attributeValue);
    }

    /**
     * zwraca nazwę atrybutu na podstawie lini z pliku konfiguracyjnego
     * @param line linijka z pliku konifiguracyjnego
     * @return nazwa atrybutu
     */
    private String getAttributeName(String line) {
        String[] separatedLine = line.split("=");
        return separatedLine[0].trim();
    }

    /**
     * zwraca wartość atrybutu na podstawie lini z pliku konfiguracyjnego
     * @param line linijka z pliku konifiguracyjnego
     * @return nazwa atrybutu
     */
    private Double getAttributeValue(String line) {
        String[] separatedLine = line.split("=");
        return Double.valueOf(separatedLine[1]);
    }

    /**
     * wybiera konifg o nazwie podanej za argument z listy uprzednio wczytanych konfigów
     * @param name nazwa konfiga
     * @throws ConfigException obsługuje wyjątki związane bezpośrednio z konfigiem
     */
    private void selectConfig(String name) throws ConfigException {
        for(GameConfig config : configs)
            compareConfigName(config, name);
    }

    /**
     * sprawdza czy congig ma podaną nazwę jeśli tak, ustawia go jako aktualnie szukanego
     * @param config sprawdzany config
     * @param name nazwa konfiga
     */
    private void compareConfigName(GameConfig config, String name) {
        if(config.hasName(name))
            currentConfig = config;
    }

    /**
     * sprawdza poprawność konfiga o nazwie
     * @param name nazwa wybranego konfiga
     * @throws ConfigException obsługuje wyjątki związane bezpośrenio z konfigiem
     */
    private void checkSelectedConfig(String name) throws ConfigException {
        if(!isCorrectlyLoadedConfig(name))
            throw ConfigException.missingConfigInPath(name, configPath);
    }

    /**
     * sprawdza czy konifg jest prawidłowo załadowany
     * @param name sprawdzany config
     * @return wynik sprawdzania czy konifg jest prawidłowo załadowany
     */
    private boolean isCorrectlyLoadedConfig(String name) {
        return currentConfig != null && currentConfig.hasName(name);
    }


}