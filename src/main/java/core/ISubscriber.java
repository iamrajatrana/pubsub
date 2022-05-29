package core;

public interface ISubscriber {

    String getID();
    void consume(Message message) throws InterruptedException;

}
