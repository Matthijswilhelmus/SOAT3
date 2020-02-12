package Observer;

public interface Observable {
    void subscribeObserver(Observer observer);

    void unsubscribeObserver(Observer observer);

    void notifyObservers();
}
