package com.jaziel.common.zookeeper.sequence;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author 王杰
 * @date 2021/6/8 19:54
 */
public class ZkSequence {
    DistributedAtomicLong distributedAtomicLong;

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(500, 3);

    public ZkSequence(CuratorFramework client, String counterPath) {
        distributedAtomicLong = new DistributedAtomicLong(client, counterPath, retryPolicy);
    }

    /**
     * 生成序列
     *
     * @return
     * @throws Exception
     */
    public Long sequence() throws Exception {
        AtomicValue<Long> increment = this.distributedAtomicLong.increment();
        if (increment.succeeded()) {
            return increment.postValue();
        } else {
            return null;
        }
    }
}
