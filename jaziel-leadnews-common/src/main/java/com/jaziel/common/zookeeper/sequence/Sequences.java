package com.jaziel.common.zookeeper.sequence;

import com.jaziel.common.zookeeper.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 王杰
 * @date 2021/6/8 20:34
 */
@Component
public class Sequences {

    @Autowired
    private ZookeeperClient client;

    public Long sequenceApLikes() {
        return this.client.sequence(ZkSequenceEnu.AP_LIKES);
    }

    public Long sequenceApReadBehavior() {
        return this.client.sequence(ZkSequenceEnu.AP_READ_BEHAVIOR);
    }

    public Long sequenceApCollection() {
        return this.client.sequence(ZkSequenceEnu.AP_COLLECTION);
    }

    public Long sequenceApUserFollow() {
        return
                this.client.sequence(ZkSequenceEnu.AP_USER_FOLLOW);
    }

    public Long sequenceApUserFan() {
        return
                this.client.sequence(ZkSequenceEnu.AP_USER_FAN);
    }
}
