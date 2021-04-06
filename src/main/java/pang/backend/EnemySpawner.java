package pang.backend;

public class EnemySpawner {
    public static Enemy spawn(String name, Integer spawnTime) throws IllegalArgumentException{
        return switch (name) {
            case "smallBall" -> new SmallBall(spawnTime);
            case "largeBall" -> new LargeBall(spawnTime);
            default -> throw new IllegalArgumentException("Enemy name not found");
        };
    }
}
