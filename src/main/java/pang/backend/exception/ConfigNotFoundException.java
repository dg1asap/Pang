package pang.backend.exception;

import java.nio.file.Path;

public class ConfigNotFoundException extends Exception{
    private final ErrorCode errorCode;
    private String configName;
    private String attributeName;
    private Path path;

    public static ConfigNotFoundException configPath(Path path){
        return new ConfigNotFoundException(path);
    }

    public static ConfigNotFoundException noAttributeInConfig(String attributeName, String configName){
        return new ConfigNotFoundException(configName, attributeName);
    }

    public static ConfigNotFoundException missingConfigInPath(String configName, Path path){
        return new ConfigNotFoundException(configName, path);
    }

    public String errorMessage(){
        return switch (errorCode) {
            case OK -> "[ConfigNotFound] ERROR : ConfigNotFoundException was not initialization";
            case FILE_NOT_FOUND -> String.format("[ConfigNotFound] ConfigLoader : A config file with path %s was not found", path);
            case MISSING_ATTRIBUTE -> String.format("[ConfigNotFound] %sConfig : An attribute named %s in %sConfig was not found", configName, attributeName, configName);
            case MISSING_CONFIG -> String.format("[ConfigNotFound] %sConfig : A config named %s was not found in %s", configName, configName, path);
        };
    }

    public enum ErrorCode {
        OK, FILE_NOT_FOUND, MISSING_ATTRIBUTE, MISSING_CONFIG
    }

    protected ConfigNotFoundException(Path path){
        this.path = path;
        this.errorCode = ErrorCode.FILE_NOT_FOUND;
    }

    protected ConfigNotFoundException(String configName, String attributeName){
        this.configName = configName;
        this.attributeName = attributeName;
        this.errorCode = ErrorCode.MISSING_ATTRIBUTE;
    }

    protected ConfigNotFoundException(String configName, Path path){
        this.configName = configName;
        this.path = path;
        this.errorCode = ErrorCode.MISSING_CONFIG;
    }

}