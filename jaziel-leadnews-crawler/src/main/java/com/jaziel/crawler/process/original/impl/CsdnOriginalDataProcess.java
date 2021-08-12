package com.jaziel.crawler.process.original.impl;

import com.jaziel.crawler.config.CrawlerConfig;
import com.jaziel.crawler.process.entity.ProcessFlowData;
import com.jaziel.crawler.process.original.AbstractOriginalDataProcess;
import com.jaziel.model.crawler.core.parse.ParseItem;
import com.jaziel.model.crawler.core.parse.impl.CrawlerParseItem;
import com.jaziel.model.crawler.enums.CrawlerEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Jaziel
 * @date 2021/7/24 18:05
 */
@Component
@Log4j2
public class CsdnOriginalDataProcess extends AbstractOriginalDataProcess {
    @Autowired
    private CrawlerConfig crawlerConfig;

    @Override
    public List<ParseItem> parseOriginalRequestData(ProcessFlowData processFlowData) {
        List<ParseItem> parseItemList = null;
        List<String> initCrawlerUrlList = crawlerConfig.getInitCrawlerUrlList();
        if (initCrawlerUrlList != null && !initCrawlerUrlList.isEmpty()) {
            parseItemList = initCrawlerUrlList.stream().map(url -> {
                CrawlerParseItem parseItem = new CrawlerParseItem();
                url = url + "?rnd=" + System.currentTimeMillis();
                parseItem.setUrl(url);
                parseItem.setDocumentType(CrawlerEnum.DocumentType.INIT.name());
                parseItem.setHandelType(processFlowData.getHandelType().name());
                return parseItem;
            }).collect(Collectors.toList());
        }
        return parseItemList;
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
