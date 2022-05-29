package core;

import java.util.ArrayList;

public class Broker {

    ArrayList<Topic> topics = new ArrayList<>();

    public void createTopic(String mytopic) {
        topics.add(new Topic(mytopic));
    }

    public Topic getTopic(String mytopic) {
        return topics.stream().
                filter(topic -> topic.getName().equals(mytopic))
                .findFirst().get();
    }
}
