package pang.backend.properties.info;

/**
 * interfejs oznaczający że klasa implementująca go jest w stanie zwracać obiekty typu GameInfo
 */
public interface Info {
    /**
     * zwraca obiekt typu gameInfo
     * @return obiekt typu gameInfo
     */
    GameInfo getGameInfo();
}
