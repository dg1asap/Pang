package pang.backend.character;

import pang.backend.properties.config.GameConfig;
import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca postać w grze
 */
public abstract class Character implements HitBox, ResizeObserver {
    /**
     * mapa, która zawiera statyski postaci wraz z wartościami
     */
    private final Map<String, Double> stats = new HashMap<>();
    /**
     * cooldown
     */
    protected CoolDown coolDown;

    /**
     * tworzy postać na podstawie obiektu konfigurującego oraz cooldown'a
     * @param config konfiguracja
     * @param coolDown cooldown
     */
    public Character(GameConfig config, CoolDown coolDown){
        addStat(config,"health", "damage", "speed", "height", "width", "posX", "posY", "score");
        this.coolDown = coolDown;
    }

    /**
     * dodaje statkę lub statysytki postaci, wczytując przytym jej wartość z konfiguracji
     * @param config konfiguracja
     * @param newStats nazwa nowej statystyki
     */
    protected void addStat(GameConfig config, String... newStats) {
        for(String stat : newStats) {
            stats.put(stat, config.getAttribute(stat));
        }
    }

    /**
     * zwraca wartość odpowiadająca podanej za argument statystyce
     * @param statName nazwa szukanej statysyki
     * @return wartość odpowiadająca statystyce
     */
    protected Double getStat(String statName) {
        return stats.get(statName);
    }

    /**
     * zwiększa wartość statysyki o podaną za argument
     * @param stat nazwa statystyki
     * @param value wartość o którą statystyka ma wzrosnąć
     */
    protected void increaseStatByValue(String stat, double value){
        stats.computeIfPresent(stat, (k, v) -> v + value);
    }

    /**
     * sklauje statystykę do składowej X wektora zmiany rozmiaru mapy
     * @param statName nazwa statystyki
     * @param size wektor rozmiaru mapy
     */
    protected void scaleStatToX(String statName, PangVector size) {
        double stat = getStat(statName);
        double scaledStat = size.getScaledXof(stat);
        stats.replace(statName, scaledStat);
    }

    /**
     * sklauje statystykę do składowej Y wektora zmiany rozmiaru mapy
     * @param statName nazwa statystyki
     * @param size wektor rozmiaru mapy
     */
    protected void scaleStatToY(String statName, PangVector size) {
        double stat = getStat(statName);
        double scaledStat = size.getScaledYof(stat);
        stats.replace(statName, scaledStat);
    }

    /**
     * zwraca pozycję na mapie
     * @return wektor reprezentujący punkt na mapie w, którym przebywa postaci
     */
    public PangVector getPosition() {
        int intPosX = getStat("posX").intValue();
        int intPosY = getStat("posY").intValue();
        return new PangVector(intPosX, intPosY);
    }

    /**
     * metoda informująca o stanie życia postaci
     * @return jeśli true oznacza jest martwy, false żywy
     */
    public boolean isDead(){
        return stats.get("health") < 0;
    }

    /**
     * postać atakuje postać podaną w argumencie metody
     * @param character postać którą atakuje nasza postać
     */
    public void attack(Character character) {
        if (characterCanAttack()) {
            damageCharacter(character);
            stealPoints(character);
        }
    }

    /**
     * rysuje wygląd postaci
     * @param g obiekt typu Graphics
     */
    public abstract void draw(Graphics g);

    /**
     * przeskalowanuje rozmiar gracza do wektora
     * @param size wektor skalujący aktualny rozmiar postaci
     */
    @Override
    public void resize(PangVector size) {
        scaleStatToX("posX", size);
        scaleStatToY("posY", size);
        scaleStatToX("width", size);
        scaleStatToY("height", size);
    }

    /**
     * metoda informująca czy postać może atakować
     * @return informuje czy metoda czy postać może atakować
     */
    private boolean characterCanAttack() {
        return !coolDown.isCoolDown("attack");
    }

    /**
     * postać zadaje obrażenia postaci podanej za argument
     * @param target cel, który dozna obrażeń
     */
    private void damageCharacter(Character target) {
        double damage = this.getStat("damage");
        target.increaseStatByValue("health", -damage);
    }

    /**
     * jeśli ostatni cios był śmiertelny, postać zabierze punkty ofierze
     * @param target cel, którą postać prubuje okraść
     */
    private void stealPoints(Character target) {
        if (target.isDead()) {
            Double score = target.getStat("score");
            this.increaseStatByValue("score", score);
        }
    }

}
