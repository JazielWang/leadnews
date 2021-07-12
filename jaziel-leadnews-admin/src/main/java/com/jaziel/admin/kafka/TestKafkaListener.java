package com.jaziel.admin.kafka;

import com.jaziel.common.kafka.KafkaListener;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class TestKafkaListener implements KafkaListener<String, String> {
    @Override
    public String topic() {
        return "topic.test";
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data, Consumer<?, ?> consumer) {
        System.out.println("===========receive test message: " + data);
    }
}