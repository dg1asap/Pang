package pang.backend.character.enemy;

import pang.backend.character.CoolDown;
import pang.backend.util.PangVector;
import pang.backend.properties.config.GameConfig;
import pang.backend.world.WorldBorder;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

/**
 * Abstrakcyjny rodzaj wrogów - piłka (przeciwnik o okrągłym kształcie)
 */
public abstract class Ball extends Enemy{
    /**
     * wektor ruchu piłki
     */
    private final PangVector vectorMovement;

    /**
     * tworzy piłę na podstawie obiektu konfiguracyjnego, cooldownu, o konkretnym czasie spawnu
     * @param config plik konfiguracyjny
     * @param coolDown cooldown
     * @param spawnTime czas spawnu
     */
    public Ball(GameConfig config, CoolDown coolDown, int spawnTime) {
        super(config, coolDown, spawnTime);
        vectorMovement = PangVector.randPangVector(-10, 10);
    }

    /**
     * porusza piłką wewnątrz wyznaczonych granic
     * @param border granice według których porusza się piłka
     */
    @Override
    public void moveInsideBorder(WorldBorder border) {
        if (!coolDown.isCoolDown("move")) {
            bounceOffVerticalWall(border);
            bounceOffHorizontalWall(border);
            changePosition();
        }
    }

    /**
     * zwraca hitbox piłki, hitbox jest eliptyczny
     * @return eliptyczny hitbox piłki
     */
    @Override
    public RectangularShape getHitBox() {
        double posX = getStat("posX");
        double posY = getStat("posY");
        double width = getStat("width");
        double height = getStat("height");
        return new Ellipse2D.Double(posX, posY, width, height);
    }

    /**
     * odbicie się piłki
     */
    public void bounceOff() {
        if (!coolDown.isCoolDown("bounceOff")) {
            vectorMovement.invertX();
            vectorMovement.invertY();
        }
    }

    /**
     * odbicie piłi od pionowej sciany granicy
     * @param wall pionowe granice mapy
     */
    private void bounceOffVerticalWall(WorldBorder wall) {
        if (!wall.isInBorderOfWorld(this, "posX", vectorMovement.getX()))
            vectorMovement.invertX();
    }

    /**
     * odbicie piłki od poziomej przeszkody
     * @param wall poziome granice mapy
     */
    private void bounceOffHorizontalWall(WorldBorder wall) {
        if (!wall.isInBorderOfWorld(this, "posY", vectorMovement.getY()))
            vectorMovement.invertY();
    }

    /**
     * zmienia położenie piłki po mapie, zgodnie z nadanym wektorem ruchu
     */
    private void changePosition() {
        double intVectorX = vectorMovement.getX();
        double intVectorY = vectorMovement.getY();
        increaseStatByValue("posX", intVectorX);
        increaseStatByValue("posY", intVectorY);
    }


}
