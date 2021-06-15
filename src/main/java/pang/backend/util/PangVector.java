package pang.backend.util;

import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;

import java.util.Random;

/**
 * klas pomocnicza reprezentująca wektor w grze
 */
public class PangVector {
    /**
     * pozycja x
     */
    private int x;
    /**
     * pozycja y
     */
    private int y;
    /**
     * delta pozycji x
     */
    private int oldX = 1;
    /**
     * delta pozycji y
     */
    private int oldY = 1;

    /**
     * tworzy losowy wektor o długosci z zakresu min - max nie można wylosować składowej 0.
     * @param min minimalny zakres losowania długości składowej
     * @param max maksymalny zakres losowania długości składowej
     * @return losowy wektor
     */
    public static PangVector randPangVector(int min, int max) {
        int x, y;
        do {
            Random rand = new Random();
            x = rand.nextInt((max - min) + 1) + min;
            y = rand.nextInt((max - min) + 1) + min;
        } while (x == 0 || y == 0);
        return new PangVector(x, y);
    }

    /**
     * Losuje składową wektora, może wylosować 0
     * @param min minimalny zakres losowania długości składowej
     * @param max maksymalny zakres losowania długości składowej
     * @return int reprezentujący składową wektora
     */
    public static int randComponentOfVector(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * tworzy wektor o składowe x i y
     * @param x składowa x
     * @param y składowa y
     */
    public PangVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** tworzy wektor o składowej x i y oraz zmianie wektora x i zmianie wektora y
     * @param x składowa x
     * @param y składowa y
     * @param oldX delta x
     * @param oldY delta y
     */
    public PangVector(int x, int y, int oldX, int oldY) {
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    /**
     * zwraca składową x
     * @return składowa x
     */
    public int getX() {
        return x;
    }

    /**
     * zwraca składową y
     * @return składowa y
     */
    public int getY() {
        return y;
    }

    /**
     * odwraca składową x
     */
    public void invertX() {
        x = -x;
    }

    /**
     * odwraca składową y
     */
    public void invertY() {
        y = -y;
    }

    /**
     * skaluje argument do wartości składowej wektora x
     * @param value wartość skalowana
     * @return przeskalowana wartość
     */
    public double getScaledXof(double value) {
        double xRatio = (double) x / (double) oldX;
        return value * xRatio;
    }

    /**
     * skaluje argument do wartości składowej wektora y
     * @param value wartość skalowana
     * @return przeskalowana wartość
     */
    public double getScaledYof(double value) {
        double yRatio = (double) y / (double) oldY;
        return value * yRatio;
    }

    /**
     * skaluje argument do wartość składowej wektora x względem wartości początkowej
     * @param value wartość skalowana
     * @return przeskalowana wartość
     */
    public double getScaledToInitialXof(double value) {
        GameConfig pangConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        double xRatio = (double) x / pangConfig.getAttribute("defaultXFrameSize");
        return value * xRatio;
    }

    /**
     * skaluje argument do wartość składowej wektora y względem wartości początkowej
     * @param value wartość skalowana
     * @return przeskalowana wartość
     */
    public double getScaledToInitialYof(double value) {
        GameConfig pangConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        double yRatio = (double) y / pangConfig.getAttribute("defaultYFrameSize");
        return value * yRatio;
    }


}
