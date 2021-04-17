package pang.backend.exception;

public class ConfigNotFoundException extends Exception{
    private final String configName;

    public ConfigNotFoundException(String configName){
        this.configName = configName;
    }

    public String errorMessage() {
        return String.format("ConfigLoader didn't find %sConfig",configName);
    }
}
