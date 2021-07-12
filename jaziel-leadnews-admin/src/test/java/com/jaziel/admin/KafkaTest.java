package com.jaziel.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaTest {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void test() {
        try {
            this.kafkaTemplate.send("topic.test", "123key", "jaziel");
            System.out.println("==============消息发送了===================");
            Thread.sleep(500000);// 休眠等待消费者接收消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}