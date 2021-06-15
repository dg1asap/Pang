package pang.backend.bullet;

import pang.backend.character.HitBox;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * Klas reprezentująca nabój w grze
 */
public class Bullet implements HitBox, ResizeObserver {
    /**
     * pozycja na osi y
     */
    private double xPosition;
    /**
     * pozycja na osi x
     */
    private double yPosition;
    /**
     * szerokość naboju
     */
    private double width;
    /**
     * wysokość naboju
     */
    private double height;

    /**
     * Konstruktor naboju za argumenty przyjmuje pozycje na mapie w osi x i y a także jego rozmiar
     * @param xPosition pozycja na osi x
     * @param yPosition pozycja na osi y
     * @param size rozmiar pocisku
     */
    public Bullet(double xPosition, double yPosition, PangVector size){
        GameConfig bulletConfig = ConfigLoader.CONFIG_LOADER.getConfig("Bullet");
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = bulletConfig.getAttribute("width");
        this.height = bulletConfig.getAttribute("height");
        width = size.getScaledToInitialXof(width);
        height = size.getScaledToInitialYof(height);
    }

    /**
     * Metoda rysująca wygląd i rozmiar pocisku oraz kolorująca go na czarno za argument przyjmuje Graphic
     * @param g argument typu Graphic
     */
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect((int)xPosition,(int)yPosition,(int) width,(int) height);
    }

    /**
     * Metoda wystrzeliwująca pocisk, symuluje trajektorie lotu pocisku
     */
    public void fire(){
        yPosition = yPosition - 1;
    }

    /**
     * Metoda zwraca pozycje w osi Y naboju
     * @return pozycja Y naboju
     */
    public int getY(){
        return (int)yPosition;
    }

    /**
     * Metoda zwracająca Hitbox, pocisku
     * @return hitbox
     */
    @Override
    public RectangularShape getHitBox() {
        return new Rectangle2D.Double(xPosition, yPosition, width, height);
    }

    /**
     * Metoda ustawiająca początkowy przeskalowanie pocsiku z interfesju resizeObserver
     * @param size wektor skalujacy aktualny rozmiar pocisku
     */
    @Override
    public void initialResize(PangVector size) {}

    /**
     * Metdoa skalujaca aktualny rozmiar pocisku
     * @param size wektor skalujacy aktualny rozmiar pocisku
     */
    @Override
    public void resize(PangVector size) {
        xPosition = size.getScaledXof(xPosition);
        yPosition = size.getScaledYof(yPosition);
        width = size.getScaledXof(width);
        height = size.getScaledYof(height);
    }
}
