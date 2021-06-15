package pang.backend.world;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.GameInfoFactory;
import pang.backend.properties.info.Info;

/**
 * klasa generująca raport w postaci obiektu GameInfo dla świata
 */
public class WorldInfoFactory extends GameInfoFactory {
    /**
     * Tworzy obiekt GameInfo
     */
    public WorldInfoFactory() {
        info = new GameInfo("World");
    }

    /**
     * tworzy info dla klasy implementującej interfejs Info
     * @param world świat, który implementuje interfejs info
     * @return obiekt typu GameInfo
     */
    @Override
    public GameInfo create (Info world) {
        World castedWorld = (World) world;
        addEndingForWorld(castedWorld);
        return info;
    }

    /**
     * dodaje do obiektu typu GameInfo informacje dotyczące końca gry
     * @param world świat
     */
    private void addEndingForWorld(World world) {
        if (world.isEmpty())
            info.addAttribute("ending", "win");
        if (world.isGameOver())
            info.addAttribute("ending", "lose");
    }


}
