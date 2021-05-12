package pang.backend.character;

import java.util.Random;

public class PangPosition{
    private int x;
    private int y;

    public static PangPosition randPangVector(int min, int max) {
        Random rand = new Random();
        int x = rand.nextInt((max - min) + 1) + min;
        int y = rand.nextInt((max - min) + 1) + min;

        return new PangPosition(x, y);
    }

    public PangPosition(int x, int y) {
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
