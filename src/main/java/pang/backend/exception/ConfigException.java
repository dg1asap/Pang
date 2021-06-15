package pang.backend.exception;

import java.nio.file.Path;

/**
 * Klasa obsługująca sytuacje wyjątkowe podczas wczytywania i zwracania obiektów konfiguracyjnych
 */
public class ConfigException extends Exception {
    /**
     * kod błędu
     */
    private final ErrorCode errorCode;
    /**
     * nazwa obiektu konfiguracji
     */
    private String configName;
    /**
     * nazwa atrybutu
     */
    private String attributeName;
    /**
     * ścieżka do konfiga
     */
    private Path path;

    /**
     * według wzorca metoda wytwórcza, tworzy obiekt ConfigExcpetion na podstawie ścieżki
     * @param path ścieżka do konfiga
     * @return obiekt typu ConfigException
     */
    public static ConfigException configPath(Path path) {
        return new ConfigException(path);
    }

    /**
     * według wzorca metoda wytwórcza, tworzy obiekt ConfigExcpetion na podstawie atrybutu oraz jego nazwy
     * @param attributeName nazwa atrybutu
     * @param configName nazwa obiektu konfiguracji
     * @return wyjątek typu ConfigException
     */
    public static ConfigException noAttributeInConfig(String attributeName, String configName) {
        return new ConfigException(configName, attributeName);
    }

    /**
     * według wzorca metoda wytwórcza, tworzy obiekt ConfigExcpetion na podstawie jego nazwy oraz ścieżki do niego
     * @param configName nazwa obiektu konfiguracji
     * @param path ścieżka do obiektu konfiguracji
     * @return wyjątek typu ConfigException
     */
    public static ConfigException missingConfigInPath(String configName, Path path) {
        return new ConfigException(configName, path);
    }

    /**
     * zwraca informację o błędzie
     * @return zwraca informację o błędzie
     */
    public String errorMessage() {
        return switch (errorCode) {
            case OK -> "[ConfigNotFound] ERROR : ConfigNotFoundException was not initialization properly";
            case FILE_NOT_FOUND -> String.format("[ConfigNotFound] ConfigLoader : A config file with path %s was not found", path);
            case MISSING_ATTRIBUTE -> String.format("[ConfigNotFound] %sConfig : An attribute named %s in %sConfig was not found", configName, attributeName, configName);
            case MISSING_CONFIG -> String.format("[ConfigNotFound] %sConfig : A config named %s was not found in %s", configName, configName, path);
        };
    }

    /**
     * tworzy obiekt na podstawie ścieżki
     * @param path ścieżka do konfiga
     */
    protected ConfigException(Path path) {
        this.path = path;
        this.errorCode = ErrorCode.FILE_NOT_FOUND;
    }

    /**
     * tworzy obiekt na podstawie nazwy konfiga oraz atrybutu
     * @param configName nazwa konfiga
     * @param attributeName atrybut
     */
    protected ConfigException(String configName, String attributeName) {
        this.configName = configName;
        this.attributeName = attributeName;
        this.errorCode = ErrorCode.MISSING_ATTRIBUTE;
    }

    /**
     * tworzy obiekt na podstawie nazwy konfigu oraz jego ścieżki
     * @param configName nazwa konfigu
     * @param path ścieżka od konfigu
     */
    protected ConfigException(String configName, Path path) {
        this.configName = configName;
        this.path = path;
        this.errorCode = ErrorCode.MISSING_CONFIG;
    }

    /**
     * kod błędu
     */
    private enum ErrorCode {
        OK, FILE_NOT_FOUND, MISSING_ATTRIBUTE, MISSING_CONFIG
    }

}