package pang.backend.properties.config;

import pang.backend.exception.ConfigException;
import pang.backend.properties.GameProperties;

public class GameConfig extends GameProperties {
    private double attribute;

    public GameConfig(String name) {
        super(name);
    }

    @Override
    public double getAttribute(String attributeName) {
        setAttribute(attributeName);
        return attribute;
    }

    private void setAttribute(String attributeName) {
        try {
            setAttributeNamed(attributeName);
        } catch (ConfigException e){
            logger.error(e.errorMessage());
        }
    }

    private void setAttributeNamed(String attributeName) throws ConfigException {
        if (hasAttribute(attributeName))
            this.attribute = attributes.get(attributeName);
        else
            throw ConfigException.noAttributeInConfig(attributeName, this.name);
    }

    private boolean hasAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }


}