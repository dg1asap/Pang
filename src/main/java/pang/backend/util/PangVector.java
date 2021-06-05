package pang.backend.util;

import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;

import java.util.Random;

public class PangVector {
    private int x;
    private int y;

    public static PangVector randPangVector(int min, int max) {
        int x, y;
        do {
            Random rand = new Random();
            x = rand.nextInt((max - min) + 1) + min;
            y = rand.nextInt((max - min) + 1) + min;
        } while (x == 0 || y == 0);
        return new PangVector(x, y);
    }

    public static int randComponentOfVector(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public PangVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public double getXScalingRatio() {
        GameConfig pangConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        return getX() / pangConfig.getAttribute("defaultXFrameSize");
    }

    public void invertX() {
        x = -x;
    }

    public int getY() {
        return y;
    }

    public double getYScalingRatio() {
        GameConfig pangConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        return getY() / pangConfig.getAttribute("defaultYFrameSize");
    }

    public void invertY() {
        y = -y;
    }

}
