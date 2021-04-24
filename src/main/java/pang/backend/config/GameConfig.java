package pang.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.exception.ConfigException;

import java.util.ArrayList;

public class GameConfig {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final String name;
    private final ArrayList <String> namesOfAttributes = new ArrayList<>(100);
    private final ArrayList <Double> valuesOfAttributes = new ArrayList<>(100);
    private double attribute;

    public static GameConfig byName(String name) {
        return new GameConfig(name);
    }

    public void addAttribute(String name, Double value) {
        namesOfAttributes.add(name);
        valuesOfAttributes.add(value);
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public double getAttribute(String attributeName) {
        setAttribute(attributeName);
        return this.attribute;
    }

    protected GameConfig(String name) {
        this.name = name;
    }

    private void setAttribute(String attributeName) {
        try {
            setAttributeNamed(attributeName);
        } catch (ConfigException e){
            logger.error(e.errorMessage());
        }
    }

    private void setAttributeNamed(String attributeName) throws ConfigException {
        if (hasAttribute(attributeName)) {
            int attributeIndex = getAttributeIndex(attributeName);
            this.attribute = valuesOfAttributes.get(attributeIndex);
        } else {
            throw ConfigException.noAttributeInConfig(attributeName, this.name);
        }
    }

    private boolean hasAttribute(String attribute) {
        return namesOfAttributes.contains(attribute);
    }

    private int getAttributeIndex(String attributeName) {
        return namesOfAttributes.indexOf(attributeName);
    }

}