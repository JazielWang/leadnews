package com.jaziel.media.kafka;

import com.jaziel.common.kafka.KafkaSender;
import com.jaziel.common.kafka.message.admin.SubmitArticleAuthMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author Jaziel
 * @date 2021/7/24 13:01
 */
@Component
public class AdminMessageSender {

    @Autowired
    private KafkaSender kafkaSender;

    /**
     * 只发送行为消息
     * @param message
     */
    @Async
    public void sendMessage(SubmitArticleAuthMessage message){
        kafkaSender.sendSubmitArticleAuthMessage(message);
    }
}
