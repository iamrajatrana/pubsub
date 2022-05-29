package core;

public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final SubscriberMetadata subscriberMetadata;
    private boolean running = true;

    public SubscriberWorker(Topic topic, SubscriberMetadata subscriberMetadata) {
        this.topic = topic;
        this.subscriberMetadata = subscriberMetadata;
    }

    @Override
    public void run() {

        while (running) {
            synchronized (subscriberMetadata) {
                int currentOffset = subscriberMetadata.getOffset().get();

                try {
                    while (currentOffset >= topic.getMessages().size()) {
                        subscriberMetadata.wait();
                    }
                    subscriberMetadata.getSubscriber().consume(topic.getMessages().get(currentOffset));
                    subscriberMetadata.getOffset().compareAndSet(currentOffset, currentOffset + 1);
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
        synchronized (subscriberMetadata) {
            subscriberMetadata.notify();
        }
    }
}
