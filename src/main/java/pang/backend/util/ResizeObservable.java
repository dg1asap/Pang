package pang.backend.util;

public interface ResizeObservable {
    void addResizeObserver(ResizeObserver observer);
    void removeResizeObserver(ResizeObserver observer);
    void resizeNotify();
}
