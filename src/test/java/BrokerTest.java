import core.*;
import org.junit.jupiter.api.Test;
import subscribers.SleepingSubscriber;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrokerTest {

    @Test
    void createAndSubscribe() throws InterruptedException {
        TopicRegistry.createTopic("students");
        Topic studentTopic = TopicRegistry.getTopic("students");
        studentTopic.addSubscriber(new SubscriberMetadata(new SleepingSubscriber("1", 1000)));
        studentTopic.addSubscriber(new SubscriberMetadata(new SleepingSubscriber("2", 1000)));

        studentTopic.publish(new Message("m1"));
        studentTopic.publish(new Message("m2"));
        studentTopic.publish(new Message("m3"));
        Thread.sleep(10000);
    }
}
