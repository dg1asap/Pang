package pang.backend.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class GameProperties<V> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Map<String, V> attributes = new HashMap<>();
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

    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    public void addAttribute(String name, V value) {
        attributes.put(name, value);
    }

    public V getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public void merge(GameProperties<V> gameProperties) {
        this.attributes.putAll(gameProperties.attributes);
    }

}