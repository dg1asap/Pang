package pang.backend.util;

/**
 * wzorzec obserwatora związany z zdarzeniem zmiany rozmiaru
 */
public interface ResizeObservable {
    /**
     * dodaje obserwatora zmiany rozmiaru
     * @param observer obserwator zmiany rozmiaru
     */
    void addResizeObserver(ResizeObserver observer);

    /**
     * usuwa obserwatora zmiany rozmiaru
     * @param observer obserwator zmiany rozmiaru
     */
    void removeResizeObserver(ResizeObserver observer);

    /**
     * powiadamia obserwatorów o zmiania rozmiaru
     */
    void resizeNotify();
}
