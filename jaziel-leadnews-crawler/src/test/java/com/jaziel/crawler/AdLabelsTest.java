package com.jaziel.crawler;

import com.jaziel.crawler.servicie.AdLabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Jaziel
 * @date 2021/8/6 14:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdLabelsTest {
    @Autowired
    private AdLabelService adLabelService;

    @Test
    public void test1(){
        String lableIds = adLabelService.getLableIds("java,web,xxxxx");
        System.out.println(lableIds);
    }

    @Test
    public void testGetAdChannelByLabelIds(){
        Integer adChannelByLabelIds = adLabelService.getAdChannelByLabelIds("1,2");
        System.out.println(adChannelByLabelIds);
    }
}
