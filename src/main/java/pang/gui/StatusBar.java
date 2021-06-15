package pang.gui;

import pang.backend.util.PangVector;
import pang.backend.util.ResizeObserver;

import java.awt.*;

/**
 * Klasa odpowiedzialna za wyświetlanie informacji o stanie gracza
 */
public class StatusBar implements ResizeObserver {

    /**
     * aktualny wynik gracza
     */
    private int score;

    /**
     * pozostałe hp gracza
     */
    private int health;

    /**
     * pozostała amunicja gracza
     */
    private int ammo;

    /**
     * wektor zawierający pozycję gracza
     */
    private PangVector position;

    /**
     * Pobiera informacje o playerze
     * @param score aktualny wynik
     * @param health pozostałe życie
     * @param ammo dostępna amunicja
     */
    public StatusBar(int score, int health, int ammo){
        setNewPlayerInfo(score, health, ammo);
    }

    /**
     * Ustawia aktualne informacje o playerze
     * @param score aktualny wynik
     * @param health pozostałe życie
     * @param ammo dostępna amunicja
     */
    public void setNewPlayerInfo(int score, int health, int ammo){
        setScoreAndHealth(score, health);
        setAmmo(ammo);
    }

    /**
     * Rysuje info na ekranie
     * @param g Grafika
     */
    public void draw (Graphics g){
        setFontPreferences(g);
        setContentAndPositionOfBar(g);
    }

    /**
     * Ustawia ilość życia gracza i jego wynik
     * @param score wynik
     * @param health życie
     */
    private void setScoreAndHealth(int score, int health) {
        this.score = score;
        this.health = health;
    }

    /**
     * Ustawia ilość wyświetlanej amunicji
     * @param ammo amunicja
     */
    private void setAmmo(int ammo) {
        if(ammo > 0){
            ammo = ammo - 1;
        }
        this.ammo = ammo;
    }

    /**
     * Ustawia czcionkę
     * @param g Grafika
     */
    private void setFontPreferences(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.PLAIN,15));
    }

    /**
     * Rysuje Informacje o graczu na ekranie
     * @param g Grafika
     */
    private void setContentAndPositionOfBar(Graphics g) {
        int width = position.getX() - g.getFontMetrics().stringWidth("Score: " + score);
        int height = 20;
        int verticalSpacingBetweenStats = 15;
        int horizontalSpacingBetweenFrame = -30;
        g.drawString("Score: " + score, width + horizontalSpacingBetweenFrame, height);
        g.drawString("Health: " + health, width + horizontalSpacingBetweenFrame, height + verticalSpacingBetweenStats);
        g.drawString("Ammo: " + ammo, 5, 20);
    }

    /**
     * Ustawia początkowy rozmiar wyświetlanych informacji
     * @param size rozmiar
     */
    @Override
    public void initialResize(PangVector size) {
        this.position = size;
    }

    /**
     * Ustawia nowy rozmiar wyświetlanych informacji
     * @param size rozmiar
     */
    @Override
    public void resize(PangVector size) {
        this.position = size;
    }
}
