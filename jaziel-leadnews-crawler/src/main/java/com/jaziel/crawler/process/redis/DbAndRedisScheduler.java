package com.jaziel.crawler.process.redis;

import com.jaziel.crawler.helper.CrawlerHelper;
import com.jaziel.crawler.process.ProcessFlow;
import com.jaziel.crawler.process.entity.ProcessFlowData;
import com.jaziel.crawler.servicie.CrawlerNewsAdditionalService;
import com.jaziel.model.crawler.enums.CrawlerEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @Author Jaziel
 * @date 2021/8/9 14:51
 */
@Log4j2
public class DbAndRedisScheduler extends RedisScheduler implements ProcessFlow {

    @Autowired
    private CrawlerHelper crawlerHelper;
    @Autowired
    private CrawlerNewsAdditionalService crawlerNewsAdditionalService;

    public DbAndRedisScheduler(String host) {
        super(host);
    }

    public DbAndRedisScheduler(JedisPool pool) {
        super(pool);
    }

    /**
     * 是否重复
     *
     * @param request request请求
     * @param task    任务
     * @return
     */
    @Override
    public boolean isDuplicate(Request request, Task task) {
        String handelType = crawlerHelper.getHandelType(request);
        boolean isExist = false;
        //正向统计才尽心排重
        if (CrawlerEnum.HandelType.FORWARD.name().equals(handelType)) {
            log.info("URL排重开始，URL:{},documentType:{}", request.getUrl(), handelType);
            isExist = super.isDuplicate(request, task);
            if (!isExist) {
                isExist = crawlerNewsAdditionalService.isExistsUrl(request.getUrl());
            }
            log.info("URL排重结束，URL:{}，handelType:{},isExist：{}",
                    request.getUrl(), handelType, isExist);
        } else {
            log.info("反向抓取，不进行URL排重");
        }
        return isExist;
    }

    @Override
    public void handel(ProcessFlowData processFlowData) {

    }

    @Override
    public CrawlerEnum.ComponentType getComponentType() {
        return CrawlerEnum.ComponentType.SCHEDULER;
    }

    @Override
    public int getPriority() {
        return 123;
    }
}
