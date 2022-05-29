package core;

public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;
    private boolean running = true;

    public SubscriberWorker(Topic topic, TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @Override
    public void run() {

        while (running) {
            synchronized (topicSubscriber) {
                int currentOffset = topicSubscriber.getOffset().get();

                try {
                    while (currentOffset >= topic.getMessages().size()) {
                        topicSubscriber.wait();
                    }
                    topicSubscriber.getSubscriber().consume(topic.getMessages().get(currentOffset));
                    topicSubscriber.getOffset().compareAndSet(currentOffset, currentOffset + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void wakeUpIfRequired() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
