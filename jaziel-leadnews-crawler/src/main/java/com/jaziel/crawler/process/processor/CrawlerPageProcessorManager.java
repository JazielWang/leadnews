package com.jaziel.crawler.process.processor;

import com.jaziel.crawler.helper.CrawlerHelper;
import com.jaziel.crawler.process.ProcessFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/1 15:03
 */
@Component
public class CrawlerPageProcessorManager {
    @Autowired
    private CrawlerHelper crawlerHelper;
    @Resource
    private List<AbstractCrawlerPageProcessor> abstractCrawlerPageProcessorList;

    /**
     * 初始化注入的接口排序
     */
    @PostConstruct
    private void initProcessingFlow() {
        if (null != abstractCrawlerPageProcessorList && !abstractCrawlerPageProcessorList.isEmpty()) {
            abstractCrawlerPageProcessorList.sort(new Comparator<ProcessFlow>() {
                @Override
                public int compare(ProcessFlow p1, ProcessFlow p2) {
                    if (p1.getPriority() > p2.getPriority()) {
                        return 1;
                    } else if (p1.getPriority() < p2.getPriority()) {
                        return -1;
                    }
                    return 0;
                }
            });
        }
    }

    /**
     * 处理数据
     *
     * @param page
     */
    public void handel(Page page) {
        String handelType = crawlerHelper.getHandelType(page.getRequest());
        String documentType = crawlerHelper.getDocumentType(page.getRequest());
        for (AbstractCrawlerPageProcessor abstractCrawlerPageProcessor : abstractCrawlerPageProcessorList) {
            boolean isNeedHandelType = abstractCrawlerPageProcessor.isNeedHandelType(handelType);
            boolean isNeedDocumentType = abstractCrawlerPageProcessor.isNeedDocumentType(documentType);
            if (isNeedHandelType && isNeedDocumentType) {
                abstractCrawlerPageProcessor.handelPage(page);
            }
        }
    }
}
