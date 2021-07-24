package com.jaziel.common.kafka.message.admin;

import com.jaziel.common.kafka.KafkaMessage;
import com.jaziel.model.mess.admin.SubmitArticleAuto;

public class SubmitArticleAuthMessage extends KafkaMessage<SubmitArticleAuto> {
    public SubmitArticleAuthMessage() {
    }

    public SubmitArticleAuthMessage(SubmitArticleAuto data) {
        super(data);
    }

    @Override
    public String getType() {
        return "submit-article-auth";
    }
}