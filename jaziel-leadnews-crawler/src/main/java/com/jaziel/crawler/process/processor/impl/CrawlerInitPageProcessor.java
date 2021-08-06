package com.jaziel.crawler.process.processor.impl;

import com.jaziel.crawler.process.entity.CrawlerConfigProperty;
import com.jaziel.crawler.process.processor.AbstractCrawlerPageProcessor;
import com.jaziel.model.crawler.enums.CrawlerEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/1 14:37
 */
@Component
public class CrawlerInitPageProcessor extends AbstractCrawlerPageProcessor {

    @Autowired
    private CrawlerConfigProperty crawlerConfigProperty;

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public void handelPage(Page page) {
        String initCrawlerXpath = crawlerConfigProperty.getInitCrawlerXpath();
        //抓取帮助页面List
        List<String> helpUrls = page.getHtml().xpath(initCrawlerXpath).links().all();
        addSpiderRequest(helpUrls, page.getRequest(), CrawlerEnum.DocumentType.HELP);
    }

    /**
     * 需要处理的爬取类型；初始化只处理，正向爬取
     * @param handelType
     * @return
     */
    @Override
    public boolean isNeedHandelType(String handelType) {
        return CrawlerEnum.HandelType.FORWARD.name().equals(handelType);
    }

    /**
     * 需要处理的文档类型
     * 只处理初始化的URK
     * @param documentType
     * @return
     */
    @Override
    public boolean isNeedDocumentType(String documentType) {
        return CrawlerEnum.DocumentType.INIT.name().equals(documentType);
    }
}
