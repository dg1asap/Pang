package pang.backend.bullet;

import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

/**
 * Creator pocisków
 */
public class BulletCreator implements ResizeObserver {
    /**
     * rozmiar tworzonych pocisków
     */
    private PangVector size;

    /**
     * Tworzy kreatora pocisków, który będzie tworzył pociski o rozmiarze podanym za argument
     * @param size wektor sklaujacy rozmiar pocisku
     */
    public BulletCreator(PangVector size) {
        initialResize(size);
    }

    /**
     * tworzy pocisk na mapie o akordach podanych kolejno x i y
     * @param xPosition pozycja na osi x pocisku
     * @param yPosition pozycja na osi y pocisku
     * @return pocisk
     */
    public Bullet create(int xPosition, int yPosition) {
        return new Bullet(xPosition, yPosition, size);
    }

    /**
     * przeskalowanie rozmiaru tworzonych pocisków, wykonuje się zazwyczaj tylko raz
     * @param size wektor skalujacy aktualny rozmiar pocisku
     */
    @Override
    public void initialResize(PangVector size) {
        this.size = size;
    }

    /**
     * zmiana aktualnie tworzonego rozmiaru pocisku
     * @param size wektor skalujacy aktyalny rozmiar pocisku
     */
    @Override
    public void resize(PangVector size) {
        this.size = size;
    }


}
