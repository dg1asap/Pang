package pang.backend.character;

import pang.backend.properties.config.GameConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Klasa reprezentująca cooldown, czyli czas ograniczający wystąpienie pewnego zjawiska po kolejnym wystąpieniu tego samego zjawiska
 */
public class CoolDown {
    /**
     * map, w której znajdują się ostatnie wykonane akcje
     */
    private final ConcurrentHashMap <String, Long> lastActionsTime = new ConcurrentHashMap<>();
    /**
     * konfiguracja cooldowna
     */
    private final GameConfig config;

    /**
     * tworzy cooldown o określonej konfiguracji
     * @param config konfiguracja cooldowna
     */
    public CoolDown(GameConfig config) {
        this.config = config;
    }

    /**
     * sprawdza czy jest cooldown na określoną w argumencie akcję
     * @param action nazwa akcji
     * @return informację czy jest cooldown na określoną w argumencie akcję
     */
    public boolean isCoolDown(String action) {
        long coolDown = config.getAttribute(action).longValue();
        Long lastActionTime = lastActionsTime.get(action);

        ifNoLastActionTimeAddAction(lastActionTime, action);
        return compareActionWithSystemTime(action, coolDown);
    }

    /**
     * jeśli nie ma akcji w prywatnej mapie, dodaje ją
     * @param lastActionTime czas ostatniego wystąpienia akcji
     * @param action nazwa akcji
     */
    private void ifNoLastActionTimeAddAction(Long lastActionTime, String action) {
        if(lastActionTime == null)
            addAction(action);
    }

    /**
     * dodaje akcję do prywatnej mapy
     * @param action nazwa akcji
     */
    private void addAction(String action) {
        lastActionsTime.put(action, 0L);
    }

    /**
     * porównuje cooldown na akcje w mapie do czasu systemowego
     * @param action nazwa akcji
     * @param coolDown  cooldown
     * @return wynik porównania cooldown na akcje w mapie do czasu systemowego
     */
    private boolean compareActionWithSystemTime(String action, long coolDown) {
        long time = System.currentTimeMillis();
        Long lastActionTime = lastActionsTime.get(action);
        if(time > lastActionTime + coolDown) {
            lastActionsTime.replace(action, time);
            return false;
        }
        else return true;
    }


}
