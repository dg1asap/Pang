package pang.backend;

import java.util.ArrayList;

public class GameConfig {
    private final String name;
    private ArrayList <String> namesOfAttributes = new ArrayList<>(20);
    private ArrayList <Double> valuesOfAttributes = new ArrayList<>(20);

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
        return valuesOfAttributes.get(namesOfAttributes.indexOf(attribute));
    }

}
