package pang.backend.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * klasa abstrakcyjna reprezentująca właściwości obiektów w grze
 * @param <V> typ przetrzymywanych danych w obiekcie
 */
public abstract class GameProperties<V> {
    /**
     * loger, wypisujacy błędy na konsoli
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * mapa atrybutów
     */
    protected Map<String, V> attributes = new HashMap<>();
    /**
     * nazwa obiektu
     */
    protected final String name;

    /**
     * tworzy obiekt typu GameProperties o nazwie podanej za argument
     * @param name nazwa obiektu typu GameProperties
     */
    public GameProperties(String name) {
        this.name = name;
    }

    /**
     * sprawdza czy obiekt ma nazwę
     * @param name sprawdzana nazwa
     * @return wynik sprawdzenia czy obiekt ma nazwę
     */
    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    /**
     * zwraca nazwę obiektu
     * @return nazwa obiektu
     */
    public String getName() {
        return name;
    }

    /**
     * sprawdza czy obiekt ma atrybut o nazwie
     * @param name nazwa szukanego atrybutu
     * @return wynik sprawdzenia czy obiekt ma atrybut o szukanej nazwie
     */
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    /**
     * dodaje atrybut o nazwie i wartości do obietku
     * @param name nazwa atrybutu
     * @param value wartość atrybutu
     */
    public void addAttribute(String name, V value) {
        attributes.put(name, value);
    }

    /**
     * zwraca atrybut o nazwie
     * @param attributeName nazwa atrybutu
     * @return wartość atrybutu
     */
    public V getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    /**
     * łączy z sobą dwa obiekty typu GameProperties
     * @param gameProperties obiek z którego dane zostaną skopiowane
     */
    public void merge(GameProperties<V> gameProperties) {
        this.attributes.putAll(gameProperties.attributes);
    }

}