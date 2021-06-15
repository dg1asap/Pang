package pang.backend.properties.config;

import pang.backend.exception.ConfigException;
import pang.backend.properties.GameProperties;

/**
 * Obiekt konfiguracji, zawera dane wczytane z plików konifguracyjnych, opisuje podstawowe parametry większości obiektów
 */
public class GameConfig extends GameProperties<Double> {
    /**
     * ostatnio wybrany atrybut configa
     */
    private double attribute;

    /**
     * tworzy konifg o nazwie podanej za parametr
     * @param name nazwa konfiga
     */
    public GameConfig(String name) {
        super(name);
    }

    /**
     * zwraca atrybut konifiga
     * @param attributeName nazwa szukanego konfiga
     * @return wartość szukanego konfiga
     */
    @Override
    public Double getAttribute(String attributeName) {
        setAttribute(attributeName);
        return attribute;
    }

    /**
     * ustawia ostatnio szukany atrubut
     * @param attributeName nazwa szukanego atrybutu
     */
    private void setAttribute(String attributeName) {
        try {
            setAttributeNamed(attributeName);
        } catch (ConfigException e){
            logger.error(e.errorMessage());
        }
    }

    /**
     * ustawia atrybut o nazwie
     * @param attributeName nazwa ustawianego atrybutu
     * @throws ConfigException obsługuje wyjątki związane bezpośrednio z konifgiem
     */
    private void setAttributeNamed(String attributeName) throws ConfigException {
        if (hasAttribute(attributeName))
            this.attribute = attributes.get(attributeName);
        else
            throw ConfigException.noAttributeInConfig(attributeName, this.name);
    }


}