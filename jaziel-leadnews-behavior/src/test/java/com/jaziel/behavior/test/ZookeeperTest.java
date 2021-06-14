package com.jaziel.behavior.test;

import com.jaziel.behavior.BehaviorJarApplication;
import com.jaziel.common.zookeeper.sequence.Sequences;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 王杰
 * @date 2021/6/8 20:41
 */
@SpringBootTest(classes = BehaviorJarApplication.class)
@RunWith(SpringRunner.class)
public class ZookeeperTest {

    @Autowired
    private Sequences sequences;

    @Test
    public void test1(){
        Long aLong = sequences.sequenceApReadBehavior();
        System.out.println(aLong);
    }
}
