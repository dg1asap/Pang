package pang.backend.util;

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

    public int getY() {
        return y;
    }

    public void invertX() {
        x = -x;
    }

    public void invertY() {
        y = -y;
    }

}
