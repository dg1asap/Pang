package pang.backend.character;

import pang.backend.config.ConfigLoader;
import pang.backend.config.GameConfig;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CoolDown {
    private final Map <String, Long> lastActionsTime = new HashMap<>();
    private final GameConfig config;

    public CoolDown(Path path, String configName) {
        ConfigLoader configLoader = ConfigLoader.fromConfigPath(path);
        this.config = configLoader.getConfig(configName);
    }

    public boolean isCoolDown(String action) {
        long coolDown = (long) config.getAttribute(action);
        Long lastActionTime = lastActionsTime.get(action);

        ifNoLastActionTimeAddAction(lastActionTime, action);
        return compareActionWithSystemTime(action, lastActionTime, coolDown);
    }

    private void ifNoLastActionTimeAddAction(Long lastActionTime, String action) {
        if(lastActionTime == null)
            addAction(action);
    }

    private void addAction(String action) {
        lastActionsTime.put(action, 0L);
    }

    private boolean compareActionWithSystemTime(String action, Long lastActionTime, long coolDown) {
        long time = System.currentTimeMillis();
        if(time > lastActionTime + coolDown) {
            lastActionsTime.replace(action, time);
            return false;
        }
        else return true;
    }


}
