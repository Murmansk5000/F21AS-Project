package Stage2;

public interface Observer {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    void update(); // 观察者接收到通知时调用的方法
}

