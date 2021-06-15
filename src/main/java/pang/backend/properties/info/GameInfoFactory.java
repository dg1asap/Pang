package pang.backend.properties.info;

/**
 * klas tworząca obiekty typu GameInfo
 */
public abstract class GameInfoFactory {
    /**
     * tworzone info
     */
    protected GameInfo info;

    /**
     * metoda tworząca obietk typu GameInfo dla właściciela będącego argumentem
     * @param owner właściciel
     * @return obiekt typu GameInfo
     */
    public abstract GameInfo create(Info owner);

    /**
     * aktualizacja produkowanego raportu w postaci obiektu GameInfo o połączenie go z innym zasobem raportu typu GameInfo
     * @param info obiekt typu GameInfo
     */
    public void update(GameInfo info) {
        this.info.merge(info);
    }


}
