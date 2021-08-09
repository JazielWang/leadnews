package com.jaziel.crawler.process.parse.impl;

import com.jaziel.crawler.process.parse.AbstractHtmlParsePipeline;
import com.jaziel.model.crawler.core.parse.impl.CrawlerParseItem;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Jaziel
 * @date 2021/8/9 16:05
 * 对 AbstractHtmlParsePipeline 抽象类的实现，
 * * 对应数据要转换为何种格式
 * * 具体对象要怎么存储，
 * * 数据库的存储方式
 * * <p>
 * * 对评论的抓取以及存储
 */
@Component
@Log4j2
public class CrawlerHtmlParsePipeline extends AbstractHtmlParsePipeline<CrawlerParseItem> {

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    protected void handelHemlData(CrawlerParseItem parseItem) {

    }

    @Override
    protected void preParameterHandel(Map<String, Object> itemsAll) {

    }
}
