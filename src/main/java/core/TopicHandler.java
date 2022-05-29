package core;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {

    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(Topic topic) {
        this.topic = topic;
        this.subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for(TopicSubscriber topicSubscriber: topic.getSubscribers()) {
            startSubscriberWorker(topicSubscriber);
        }
    }

    public void startSubscriberWorker(TopicSubscriber subscriber) {
        String subscriberID = subscriber.getSubscriber().getID();
        if(!subscriberWorkers.containsKey(subscriberID)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, subscriber);
            subscriberWorkers.put(subscriberID, subscriberWorker);
            new Thread(subscriberWorker).start();
        }

        final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberID);
        subscriberWorker.wakeUpIfRequired();
    }

}
