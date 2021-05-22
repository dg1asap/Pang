package pang.backend.character;

import pang.backend.properties.config.GameConfig;

import java.util.concurrent.ConcurrentHashMap;

public class CoolDown {
    private final ConcurrentHashMap <String, Long> lastActionsTime = new ConcurrentHashMap<>();
    private final GameConfig config;

    public CoolDown(GameConfig config) {
        this.config = config;
    }

    public boolean isCoolDown(String action) {
        long coolDown = (long) config.getAttribute(action);
        Long lastActionTime = lastActionsTime.get(action);

        ifNoLastActionTimeAddAction(lastActionTime, action);
        return compareActionWithSystemTime(action, coolDown);
    }

    private void ifNoLastActionTimeAddAction(Long lastActionTime, String action) {
        if(lastActionTime == null)
            addAction(action);
    }

    private void addAction(String action) {
        lastActionsTime.put(action, 0L);
    }

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
