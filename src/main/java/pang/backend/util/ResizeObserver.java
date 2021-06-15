package pang.backend.util;

/**
 * wzorzec obserwatora, związany z zdarzeniem zmiany rozmiaru
 */
public interface ResizeObserver {
    /**
     * inicjalizacyjna zmiana rozmiaru
     * @param size wektor zmany aktualnego rozmiaru
     */
    void initialResize(PangVector size);

    /**
     * zmiana rozmiaru
     * @param size wektor zmiany aktualnego rozmiaru
     */
    void resize(PangVector size);
}
