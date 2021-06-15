package pang.backend.character.player;

import pang.backend.character.Character;
import pang.backend.character.CharacterInfoFactory;
import pang.backend.character.CoolDown;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.Info;
import pang.backend.util.PangVector;
import pang.backend.world.WorldBorder;
import pang.gui.StatusBar;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * Klasa reprezentująca gracza w grze
 */
public class Player extends Character implements Info {
    /**
     * stan strzelania gracza, false - nie strzela, true - strzela
     */
    private boolean isShooting = false;
    /**
     * pasek statusu gracza
     */
    private final StatusBar statusBar;

    /**
     * tworzy gracza na podstawie konfiguracji oraz cooldown'u
     * @param config konfiguracja
     * @param coolDown cooldown
     */
    public Player(GameConfig config, CoolDown coolDown) {
        super(config, coolDown);
        addStat(config, "motionVectorX", "motionVectorY", "motionVectorBlanking",
                                    "ammunition", "gravityForce", "gravityLimit");
        turnOffShooting();
        this.statusBar = new StatusBar(0,getStat("health").intValue(),getAmmoAmount());
    }

    /**
     * zwraca hitbox gracza
     * @return hitbox
     */
    @Override
    public RectangularShape getHitBox() {
        double posX = getStat("posX");
        double posY = getStat("posY");
        double width = getStat("width");
        double height = getStat("height");
        return new Rectangle2D.Double(posX, posY, width, height);
    }

    /**
     * rysuje gracza o określonym rozmiarze, pozycji i kolorze na mapie
     * @param playerGraphic obiekt typu Graphic
     */
    @Override
    public void draw(Graphics playerGraphic) {
        playerGraphic.setColor(Color.RED);

        int dx = getStat("posX").intValue();
        int dy = getStat("posY").intValue();
        int width = getStat("width").intValue();
        int height = getStat("height").intValue();
        playerGraphic.fillRect(dx, dy, width, height);

        statusBar.draw(playerGraphic);
    }

    /**
     * zwraca informacje o statusie gracza ilosc pkt etc.
     * @return obiekt niosący informacje
     */
    @Override
    public GameInfo getGameInfo() {
        CharacterInfoFactory infoFactory = new CharacterInfoFactory();
        return infoFactory.create(this);
    }

    /**
     * sterowanie graczem przez użytkownika, przy pomocy klawiatury
     * @param keyChar wciśnięty przez użytkownika przycisk na klawiaturze
     * @param value wartość, którą niesie z sobą wciśnięcie konkretnego przyciku
     */
    public void steerKey(char keyChar, double value) {
        PlayerReaction playerReaction = new PlayerReaction();
        String playerParameter = playerReaction.fromKeyName(keyChar);
        shoot(keyChar);
        increaseStatByValue(playerParameter, value);
    }

    /**
     * sterowanie czasem, podstawowe operacje na graczu takie jak wpływ grawitacji, wygaszanie nadanego wetkora ruchu
     */
    public void steerTime() {
        setNewPlayerInfo();
        move();
        affectByGravity();
    }

    /**
     * Ogranicza poruszanie się graczem do konkretnych granic
     * @param border granice poruszania się gracza
     */
    public void limitMovement(WorldBorder border) {
        int motionVectorX = getStat("motionVectorX").intValue();
        int motionVectorY = getStat("motionVectorY").intValue();
        bounceOffRightAndLeftBorder(motionVectorX, border);
        blockGoAbroadX(motionVectorX, border);
        blockGoAbroadY(motionVectorY, border);
    }

    /**
     * zainicjowana zmiana rozmiaru gracza, związana z zmianą rozmiaru mapy, powinna wykonać jeden raz
     * @param mapSize wektor skalujący związany z rozmiarem mapy
     */
    @Override
    public void initialResize(PangVector mapSize) {
        int frameWidth = mapSize.getX();
        int frameHeight = mapSize.getY();
        int startPosX = frameWidth / 2 - getStat("width").intValue() / 2;
        int startPosY = frameHeight - getStat("height").intValue();
        increaseStatByValue("posX", startPosX);
        increaseStatByValue("posY", startPosY);
        increaseStatByValue("gravityLimit", mapSize.getY());
        statusBar.initialResize(mapSize);
    }

    /**
     * zainicjowana zmiana rozmiaru gracza, związana z zmianą rozmiaru mapy,
     * @param mapSize wektor skalujący związany z rozmiarem mapy
     */
    @Override
    public void resize(PangVector mapSize) {
        super.resize(mapSize);
        statusBar.resize(mapSize);
        double oldGravityLimit = getStat("gravityLimit");
        increaseStatByValue("gravityLimit", mapSize.getY() - oldGravityLimit);
    }

    /**
     * zwraca pozycje na osi Y gracza
     * @return pozycja na osi Y gracza
     */
    public int getActualYPlayerPosition(){
        return getStat("posY").intValue();
    }

    /**
     * zwraca miejsce z którego gracz wystreliwuje pociski
     * @return pozycja Y z której gracz wystrzeliwuje pociski
     */
    public int getBulletXPos(){
        return getStat("width").intValue()/2 + getStat("posX").intValue() - 5;
    }

    /**
     * sprawdza czy gracz może podskoczyć
     * @return wynik sprawdzenia czy gracz możę podskoczyć
     */
    public boolean canPlayerJump(){
        return !coolDown.isCoolDown("jumping");
    }

    /**
     * sprawdza czy gracz może strzelać
     * @return wynik sprawdzenia czy gracz może strzelać
     */
    public boolean canShoot() {
        return getAmmoAmount() > 0 && getShootingStatus();
    }

    /**
     * użycie na graczu grawitacji
     */
    public void useGravity(){
        if (isInGravityLimit())
            increaseStatByValue("posY", getStat("gravityForce"));
    }

    /**
     * Odbicie się gracza między prawą i lewą granica
     * @param motionVectorX prędkość poruszania się gracza w osi X
     * @param border granice mapy
     */
    private void bounceOffRightAndLeftBorder(int motionVectorX, WorldBorder border) {
        if (!border.isInBorderOfWorld(this, "motionVectorX", motionVectorX))
            increaseStatByValue("motionVectorX", -2*motionVectorX);
    }

    /**
     * Zablokowanie przekraczania granicy osi X przez gracza
     * @param motionVectorX prędkość poruszania się gracza w osi X
     * @param border granice mapy
     */
    private void blockGoAbroadX(int motionVectorX, WorldBorder border) {
        int posX = getStat("posX").intValue();
        if (!border.isInBorderOfWorld(this, "posX", motionVectorX)) {
            if (posX < 0) increaseStatByValue("posX", 1);
            if (posX > 0) increaseStatByValue("posX", -1);
        }
    }

    /**
     * Zablokowanie przekraczania granicy osi Y przez gracza
     * @param motionVectorY prędkość poruszania się gracza w osi Y
     * @param border granice mapy
     */
    private void blockGoAbroadY(int motionVectorY, WorldBorder border) {
        int posY = getStat("posY").intValue();
        if (!border.isInBorderOfWorld(this, "posY", motionVectorY)) {
            if (posY < 1) increaseStatByValue("posY", 1);
            if (posY > 1) increaseStatByValue("posY", -1);
        }
    }

    /**
     * Sprawdza czy gracz jest w strefie działania limitu grawitacyjnego
     * @return wynik sprawdzenia czy gracz jest w strefie działania limitu grawitacyjnego
     */
    private boolean isInGravityLimit() {
        double posY = getStat("posY");
        return posY < getStat("gravityLimit") - getStat("height");
    }

    /**
     * porusza graczem
     */
    private void move() {
        if (canMove())
            moveUsingMotionVectors();
    }

    /**
     * sprawdza czy gracz może się poruszać
     * @return wynik sprawdzenia czy gracz może się poruszać
     */
    private boolean canMove() {
        return !coolDown.isCoolDown("movingVector");
    }

    /**
     * poruszanie się graczem przy pomocy wektorów ruchu
     */
    private void moveUsingMotionVectors() {
        double motionVectorX = getStat("motionVectorX");
        double motionVectorY = getStat("motionVectorY");
        double motionVectorBlanking = getStat("motionVectorBlanking");
        increaseStatByValue("motionVectorX", -motionVectorX / motionVectorBlanking);
        increaseStatByValue("motionVectorY", -motionVectorY / motionVectorBlanking);
        increaseStatByValue("posX", motionVectorX);
        increaseStatByValue("posY", motionVectorY);
    }

    /**
     * wpłwa grawitacją na gracza
     */
    private void affectByGravity() {
        if(canUseGravity())
            useGravity();
    }

    /**
     * sprawdza czy na gracza można zadziałać grawitacją
     * @return wynik sprawdzenia czy na gracza można zadziałać grawitacją
     */
    private boolean canUseGravity() {
        return !isGravityCoolDown();
    }

    /**
     * sprawdza czy działa cooldown na grawitację
     * @return wynik sprawdzenia czy działa cooldown na grawitację
     */
    private boolean isGravityCoolDown() {
        return coolDown.isCoolDown("gravity");
    }

    /**
     * ustawia widoczne przez użytkownika informacje o graczu
     */
    private void setNewPlayerInfo(){
        int score = this.getStat("score").intValue();
        int health = this.getStat("health").intValue();
        statusBar.setNewPlayerInfo(score, health, getAmmoAmount());
    }

    /**
     * Zwraca liczbę posiadanych przez gracza pocisków
     * @return liczba posiadanych przez gracza pocisków
     */
    private int getAmmoAmount(){
        if(getStat("ammunition")>0){
            return getStat("ammunition").intValue();
        }
        else return 0;
    }

    /**
     * strzela jeśli gracz wcisnął przycisk strzału
     * @param keyChar wciśnięty klawisz
     */
    private void shoot(char keyChar){
        turnOffShooting();
        turnOnShootingIfKeyPressed(keyChar);
    }

    /**
     * wyłącza strzelanie
     */
    private void turnOffShooting(){
        isShooting = false;
    }

    /**
     * włącza strzelanie jeśli gracz kliknął przycisk strzału
     * @param keyChar wciśnięty klawisz
     */
    private void turnOnShootingIfKeyPressed(char keyChar){
        if(keyChar =='k' || keyChar =='K'){
            isShooting = true;
        }
    }

    /**
     * zwraca status strzelania, true - jeśli aktualnie strzela, false - jeśli nie
     * @return zwraca status strzelania, true - jeśli aktualnie strzela, false - jeśli nie
     */
    private boolean getShootingStatus(){
        return isShooting;
    }


}