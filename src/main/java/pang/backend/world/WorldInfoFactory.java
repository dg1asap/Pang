package pang.backend.world;

import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.GameInfoFactory;
import pang.backend.properties.info.Info;

public class WorldInfoFactory extends GameInfoFactory {
    public WorldInfoFactory() {
        info = new GameInfo("World");
    }

    @Override
    public GameInfo create (Info world) {
        World castedWorld = (World) world;
        addEndingForWorld(castedWorld);
        return info;
    }

    private void addEndingForWorld(World world) {
        if (world.isEmpty())
            info.addAttribute("ending", "win");
        if (world.isGameOver())
            info.addAttribute("ending", "lose");
    }


}
