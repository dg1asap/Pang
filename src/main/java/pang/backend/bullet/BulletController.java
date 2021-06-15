package pang.backend.bullet;

import pang.backend.character.Character;
import pang.backend.util.PangVector;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Klasa pełniąca opiekę nad pociskami, przechowuje oraz zarządza nimi.
 */
public class BulletController {
    /**
     * Bezpieczna dla wątków kolejak przechowująca pociski,
     */
    private final ConcurrentLinkedQueue <Bullet> bullets = new ConcurrentLinkedQueue<>();
    /**
     * Właściciel kontrolera pocisków
     */
    private final Character owner;

    /**
     * Tworzy kontroler i przypisuje do niego właściciela na zasadzie agregacji.
     * @param owner właściciel
     */
    public BulletController(Character owner){
        this.owner = owner;
    }

    /**
     * Wywołuje metodę draw każdego pocisków przetrzymywanego przez kontrolera
     * @param g obietk typu Graphics
     */
    public void draw(Graphics g){
        for (Bullet bullet : bullets)
            bullet.draw(g);
    }

    /**
     * metoda sterujaca kontrolerem, działą na zasadzie MVC, wykonuje podstawowe operacje na pociskach, takie jak usuwanie, symulowanie toru ruchu etc.
     */
    public void steer(){
        for (Bullet bullet : bullets) {
            ifBulletLeavesMapRemove(bullet);
            bullet.fire();
        }
    }

    /**
     * usuwa pocisk z kolejki prywatnej
     * @param bullet pocisk
     */
    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    /**
     * dodaje pocisk do kolejki prywatnej
     * @param bullet pocisk
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * sprawdza czy cel przecina się z pociskami. Działa na zasadzie porównania HitBox'ów. Jeśli się przetni wykonuje operacje ziwiązane z interakcją takie jak zabieranie hp usuwanie pocisku z listy
     * @param target cel z którym będą porównywane pociski
     */
    public void interact(Character target) {
        for (Bullet bullet : bullets)
            consumeBulletOnTarget(bullet, target);
    }

    /**
     * przeskalowywuje pociski w kolejce
     * @param size wektor skalujący aktualny rozmiar pocisku
     */
    public void rescaleBullets(PangVector size) {
        for (Bullet bullet : bullets)
            bullet.resize(size);
    }

    /**
     * Sprawdza czy pocisk opuścił mapę jeśli opuścił usuwa go z kolejki
     * @param bullet pocisk, który w razie opuszczenia mapy zostanie usunięty z kolejki
     */
    private void ifBulletLeavesMapRemove(Bullet bullet) {
        if(bullet.getY()<0)
            removeBullet(bullet);
    }

    /**
     * Zużywa pociks na gracza, jeśli pocisk dosięgnie celu zabiera mu hp oraz ginie
     * @param bullet konsumowany pocisk
     * @param target cel
     */
    private void consumeBulletOnTarget(Bullet bullet, Character target) {
        if (bullet.intersects(target)) {
            owner.attack(target);
            bullets.remove(bullet);
        }
    }


}
