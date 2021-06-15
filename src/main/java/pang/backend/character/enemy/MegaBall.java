package pang.backend.character.enemy;

import pang.backend.character.CoolDown;
import pang.backend.util.PangVector;
import pang.backend.properties.config.GameConfig;

import java.awt.*;

/**
 * Klasa reprezentująca piłę o dużej ilość hp i dmg w grze
 */
public class MegaBall extends Ball {
    protected MegaBall(GameConfig config, CoolDown coolDown, int spawnTime){
        super(config, coolDown, spawnTime);
    }

    /**
     * metoda rysująca wygląd piłki oraz jego koler
     * @param g obiekt typu Graphics
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(getStat("posX").intValue(), getStat("posY").intValue(), getStat("width").intValue(), getStat("height").intValue());
    }

    /**
     * inicjalizuje wielkość piłki
     * @param size wektor skalujący aktualny rozmiar piłki
     */
    @Override
    public void initialResize(PangVector size) {
        spawnEnemyAtTopOfMap(size);
    }

    /**
     * odradza piłkę na górze mapy w losowym miejscu
     * @param mapSize rozmiar mapy
     */
    private void spawnEnemyAtTopOfMap(PangVector mapSize) {
        int frameWidth = mapSize.getX();
        int posX = PangVector.randComponentOfVector(50,frameWidth - 50);
        int posY = 50;
        Double actualPosX = getStat("posX");
        Double actualPosY = getStat("posY");

        increaseStatByValue("posX", posX - actualPosX);
        increaseStatByValue("posY", posY - actualPosY);
    }


}
