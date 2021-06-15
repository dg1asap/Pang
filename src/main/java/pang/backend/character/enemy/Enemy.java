package pang.backend.character.enemy;

import pang.backend.character.Character;
import pang.backend.character.CoolDown;
import pang.backend.properties.config.GameConfig;
import pang.backend.world.WorldBorder;

/**
 *
 */
public abstract class Enemy extends Character {
    /**
     * aktualny stan wroga reprezentujacy narodzenie w świecie
     */
    private boolean spawned = false;
    /**
     * czas spawnu wroga
     */
    protected int spawnTime;

    /**
     * tworzy wroga na postawie obiektu konfiguracyjnego, cooldown'u o określonym czasie spawnu
     * @param config konfiguracja
     * @param coolDown cooldown
     * @param spawnTime czas spawnu
     */
    protected Enemy(GameConfig config, CoolDown coolDown, int spawnTime){
        super(config, coolDown);
        this.spawnTime = spawnTime;
    }

    /**
     * odradza wroga w świece jesli czas podany za argument jest mniejszy od zawartego w wrogu
     * @param time czas w grze
     */
    public void spawn(long time) {
        if (time > (long)spawnTime * 1000) {
            spawned = true;
        }
    }

    /**
     * sprawdza czy wrog jest odrodzony
     * @return true - tak, false - nie
     */
    public boolean isSpawned() {
        return spawned;
    }

    /**
     * metoda abstrakcyjna, porusza wrogiem wewnatrz ustalonych granic świata
     * @param border granice mapy
     */
    public abstract void moveInsideBorder (WorldBorder border);

}
