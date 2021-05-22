package pang.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.exception.ConfigException;

import java.util.HashMap;
import java.util.Map;

public class GameConfig {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final String name;
    private final Map <String, Double> attributes = new HashMap<>();
    private double attribute;

    public GameConfig(String name) {
        this.name = name;
    }

    public void addAttribute(String name, Double value) {
        attributes.put(name, value);
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

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