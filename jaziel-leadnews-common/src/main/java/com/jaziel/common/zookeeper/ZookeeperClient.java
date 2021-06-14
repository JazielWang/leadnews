package com.jaziel.common.zookeeper;

import com.google.common.collect.Maps;
import com.jaziel.common.zookeeper.sequence.ZkSequence;
import com.jaziel.common.zookeeper.sequence.ZkSequenceEnu;
import lombok.Getter;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/6/8 20:05
 */
@Getter
@Setter
public class ZookeeperClient {
    Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
    private String host;
    private String sequencePath;

    // 重试休眠时间
    private final int SLEEP_TIME_MS = 1000;
    // 最大重试1000次
    private final int MAX_RETRIES = 1000;
    //会话超时时间
    private final int SESSION_TIMEOUT = 30 * 1000;
    //连接超时时间
    private final int CONNECTION_TIMEOUT = 3 * 1000;

    //创建连接实例
    private CuratorFramework client = null;
    // 序列化集合
    private Map<String, ZkSequence> zkSequence = Maps.newConcurrentMap();

    public ZookeeperClient(String host, String sequencePath) {
        this.host = host;
        this.sequencePath = sequencePath;
    }

    @PostConstruct
    public void init() {
        this.client = CuratorFrameworkFactory.builder().connectString(this.getHost()).connectionTimeoutMs(CONNECTION_TIMEOUT)
                .sessionTimeoutMs(SESSION_TIMEOUT).retryPolicy(new ExponentialBackoffRetry(SLEEP_TIME_MS, MAX_RETRIES)).build();
        client.start();
        this.initZkSequence();
    }

    public void initZkSequence(){
        ZkSequenceEnu[] values = ZkSequenceEnu.values();
        for (ZkSequenceEnu value : values) {
            String name = value.name();
            String path = this.getSequencePath() + name;
            ZkSequence zkSeq = new ZkSequence(this.client, path);
            zkSequence.put(name, zkSeq);
        }
    }

    /**
     * 生成seq
     * @param name 枚举需要自增的表
     * @return
     */
    public Long sequence(ZkSequenceEnu name){
        try {
            ZkSequence seq = zkSequence.get(name.name());
            if (seq != null) {
                return seq.sequence();
            }
        }catch (Exception e){
            logger.error("获取[{}]Sequence错误:{}",name,e);
        }
        return null;
    }
}
