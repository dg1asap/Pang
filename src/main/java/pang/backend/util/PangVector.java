package pang.backend.util;

import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;

import java.util.Random;

public class PangVector {
    private int x;
    private int y;
    private int oldX = 1;
    private int oldY = 1;

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

    public PangVector(int x, int y, int oldX, int oldY) {
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public int getX() {
        return x;
    }

    public double getScaledXof(double value) {
        double xRatio = (double) x / (double) oldX;
        return value * xRatio;
    }

    public double getScaledToInitialXof(double value) {
        GameConfig pangConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        double xRatio = (double) x / pangConfig.getAttribute("defaultXFrameSize");
        return value * xRatio;
    }

    public void invertX() {
        x = -x;
    }

    public int getY() {
        return y;
    }

    public double getScaledYof(double value) {
        double yRatio = (double) y / (double) oldY;
        return value * yRatio;
    }

    public double getScaledToInitialYof(double value) {
        GameConfig pangConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        double yRatio = (double) y / pangConfig.getAttribute("defaultYFrameSize");
        return value * yRatio;
    }


    public void invertY() {
        y = -y;
    }

}
