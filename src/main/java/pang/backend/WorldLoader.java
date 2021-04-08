package pang.backend;

import java.util.Queue;

public class WorldLoader {
    public static World loadLevel(int levelNumber){
        Queue <Enemy> enemies = EnemyLoader.loadLevel(levelNumber);
        Player player = PlayerLoader.load();

        return new World(player, enemies);
    }
}
