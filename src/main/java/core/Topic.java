package core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Topic {

    private final String name;
    private final List<Message> messages = new ArrayList<>();
    private final List<SubscriberMetadata> subscribers = new ArrayList<SubscriberMetadata>();
    private ReentrantReadWriteLock messagesLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock subscribersLock = new ReentrantReadWriteLock();
    private final TopicHandler topicHandler;


    public Topic(String name) {
        this.name = name;
        this.topicHandler = new TopicHandler(this);
    }

    public String getName() {
        return name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<SubscriberMetadata> getSubscribers() {
        return subscribers;
    }

    public void publish(Message message) {
        try {
            messagesLock.writeLock().lock();
            messages.add(message);
            topicHandler.publish();
        } finally {
            messagesLock.writeLock().unlock();
        }
    }

    public void addSubscriber(SubscriberMetadata subscriber) {
        try {
            subscribersLock.writeLock().lock();
            subscribers.add(subscriber);
            topicHandler.startSubscriberWorker(subscriber);
        } finally {
            subscribersLock.writeLock().unlock();
        }
    }
}
