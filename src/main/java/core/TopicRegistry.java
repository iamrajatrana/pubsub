package core;

import java.util.concurrent.ConcurrentHashMap;

public class TopicRegistry {
    static ConcurrentHashMap<String, Topic> topics = new ConcurrentHashMap<>();

    public static void createTopic(String topicName) {
        topics.putIfAbsent(topicName, new Topic(topicName));
    }

    public static Topic getTopic(String topicName) {
        return topics.get(topicName);
    }

}
