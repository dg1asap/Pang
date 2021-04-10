package pang.backend;

import java.util.Queue;

public class WorldLoader {
    public static World loadLevel(int levelNumber){
        Queue <Enemy> enemies = EnemyLoader.loadLevel(levelNumber);

        GameConfig config = new GameConfig("Game config");
        Player player = new Player(config);

        return new World(player, enemies);
    }
}
