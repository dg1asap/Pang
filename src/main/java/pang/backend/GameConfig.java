package pang.backend;

import java.util.ArrayList;

public class GameConfig {
    private final String name;
    private final ArrayList <String> namesOfAttributes = new ArrayList<>(100);
    private final ArrayList <Double> valuesOfAttributes = new ArrayList<>(100);

    GameConfig(String name){
        this.name = name;
    }

    public void addAttribute(String name, Double value){
        namesOfAttributes.add(name);
        valuesOfAttributes.add(value);
    }

    public String getName() {
        return name;
    }

    public double getAttribute(String attribute){
        int attributeIndex = getAttributeIndex(attribute);
        return valuesOfAttributes.get(attributeIndex);
    }

    public boolean hasName(String name){
        return this.name.equals(name);
    }

    private int getAttributeIndex(String attributeName){
        return namesOfAttributes.indexOf(attributeName);
    }
}
