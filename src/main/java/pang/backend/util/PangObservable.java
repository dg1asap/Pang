package pang.backend.util;

public interface PangObservable {
    void addPangObserver(PangObserver observer);
    void removePangObserver(PangObserver observer);
    void notifyPang();
}
