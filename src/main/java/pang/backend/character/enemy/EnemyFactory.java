package pang.backend.character.enemy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pang.backend.character.CoolDown;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.config.ConfigLoader;

/**
 * Klasa produkująca wrogów
 */
public class EnemyFactory {
    /**
     * Loger błedów, wypisuje wyjątki na konsoli
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * obiekt konfiguracyjny aktualnie tworzoneg worga
     */
    private GameConfig enemyConfig;
    /**
     * cooldown atkualnie tworzonego wroga
     */
    private CoolDown enemyCoolDown;
    /**
     * imie aktualnie tworzonego wroga
     */
    private String enemyName;
    /**
     * czas spawnu aktualnie tworzonego wroga
     */
    private int spawnTime;

    /**
     * tworzy wroga o określonym imieniu i czas spawnu
     * @param name imie wroga
     * @param spawnTime czas spawnu
     * @return wróg
     * @throws IllegalArgumentException zwraca inforamce w razie wpisania błędnych danych do tworzenie wroga
     */
    public Enemy createEnemyWithNameAndRespawnTime(String name, Integer spawnTime) throws IllegalArgumentException {
        setEnemyNameAndSpawnTime(name, spawnTime);
        setEnemyConfig(name);
        setEnemyCoolDown(name);
        return tryGetEnemy();
    }

    /**
     * ustawia imie oraz czas spawnu aktualnie tworzonego wroga
     * @param name imie wroga
     * @param spawnTime czas spawnu wroga
     */
    private void setEnemyNameAndSpawnTime(String name, int spawnTime) {
        this.enemyName = name;
        this.spawnTime = spawnTime;
    }

    /**
     * ustawia konfiguracje aktualnie tworzonego wroga
     * @param name imię wroga
     */
    private void setEnemyConfig(String name) {
        enemyConfig = ConfigLoader.CONFIG_LOADER.getConfig(name);
    }

    /**
     * ustawia cooldown aktualnie tworzonego wroga
     * @param name imię wroga
     */
    private void setEnemyCoolDown(String name) {
        GameConfig coolDownConfig = ConfigLoader.CONFIG_LOADER.getConfig(name + "CoolDown");
        this.enemyCoolDown = new CoolDown(coolDownConfig);
    }

    /**
     * próbuje zwrócić wroga w razie błędu zwraca na konsolę informację o błędzie
     * @return stworzonego worga
     */
    private Enemy tryGetEnemy() {
        try {
            return getEnemy();
        } catch (IllegalArgumentException e) {
            logger.error("[EnemyLoader] Enemy named " + enemyName + " was not found");
        }
        return null;
    }

    /**
     * zwraca wroga
     * @return wróg
     * @throws IllegalArgumentException zwraca na konsolę informacje o błedzie
     */
    private Enemy getEnemy() throws IllegalArgumentException {
        return switch (enemyName) {
            case "SmallBall" -> new SmallBall(enemyConfig, enemyCoolDown, spawnTime);
            case "LargeBall" -> new LargeBall(enemyConfig, enemyCoolDown, spawnTime);
            case "MegaBall" -> new MegaBall(enemyConfig, enemyCoolDown, spawnTime);
            default -> throw new IllegalArgumentException();
        };
    }


}