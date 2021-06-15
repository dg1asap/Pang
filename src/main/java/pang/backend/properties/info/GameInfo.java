package pang.backend.properties.info;

import pang.backend.properties.GameProperties;

/**
 * interfejs na obiekty zwracające raprot w postaci obietku GameInfo
 */
public class GameInfo extends GameProperties<String> {
    /**
     * tworzy obiekt o nazwie
     * @param name nazwa tworzonego obiektu
     */
    public GameInfo(String name) {
        super(name);
    }
}
