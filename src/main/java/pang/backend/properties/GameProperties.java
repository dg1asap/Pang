package pang.backend.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class GameProperties {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Map<String, Double> attributes = new HashMap<>();
    protected final String name;

    public GameProperties(String name) {
        this.name = name;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public void addAttribute(String name, Double value) {
        attributes.put(name, value);
    }

    public double getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

}