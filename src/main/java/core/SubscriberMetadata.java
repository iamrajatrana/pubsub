package core;

import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberMetadata {

    private final AtomicInteger offset;
    private final ISubscriber subscriber;

    public SubscriberMetadata(ISubscriber subscriber) {
        this.offset = new AtomicInteger(0);
        this.subscriber = subscriber;
    }

    public AtomicInteger getOffset() {
        return offset;
    }

    public ISubscriber getSubscriber() {
        return subscriber;
    }
}
