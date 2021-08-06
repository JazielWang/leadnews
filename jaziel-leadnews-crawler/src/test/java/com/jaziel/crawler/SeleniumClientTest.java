package com.jaziel.crawler;

import com.jaziel.crawler.utils.SeleniumClient;
import com.jaziel.model.crawler.core.cookie.CrawlerHtml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeleniumClientTest {
    @Autowired
    private SeleniumClient seleniumClient;

    @Test
    public void test() {
        CrawlerHtml crawlerHtml =
                seleniumClient.getCrawlerHtml("http://www.baidu.com", null, null);
        System.out.println(crawlerHtml.getHtml());
    }
}