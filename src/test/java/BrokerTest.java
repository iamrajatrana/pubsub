import core.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrokerTest {

    @Test
    void createBroker() {
        Broker broker = new Broker();
        assertTrue(broker != null);
    }

    @Test
    void createTopic() {
        Broker broker = new Broker();
        broker.createTopic("mytopic");
    }

    @Test
    void createAndSubscribe() throws InterruptedException {
        TopicRegistry.createTopic("students");
        Topic studentTopic = TopicRegistry.getTopic("students");
        studentTopic.addSubscriber(new TopicSubscriber(new SleepingSubscriber("1", 1000)));
        studentTopic.addSubscriber(new TopicSubscriber(new SleepingSubscriber("2", 1000)));

        studentTopic.publish(new Message("m1"));
        studentTopic.publish(new Message("m2"));
        studentTopic.publish(new Message("m3"));

    }
}
