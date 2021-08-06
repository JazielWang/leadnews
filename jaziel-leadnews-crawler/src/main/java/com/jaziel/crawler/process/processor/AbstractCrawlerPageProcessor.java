package com.jaziel.crawler.process.processor;

import com.jaziel.crawler.helper.CrawlerHelper;
import com.jaziel.crawler.process.AbstractProcessFlow;
import com.jaziel.crawler.process.entity.ProcessFlowData;
import com.jaziel.crawler.utils.ParseRuleUtils;
import com.jaziel.model.crawler.core.parse.ParseItem;
import com.jaziel.model.crawler.core.parse.ParseRule;
import com.jaziel.model.crawler.core.parse.impl.CrawlerParseItem;
import com.jaziel.model.crawler.enums.CrawlerEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Jaziel
 * @date 2021/8/1 14:27
 * 页面数据处理类，将通过 ProxyHttpClientDownloader 类下载的数据进行解析处理，
 * 从crawlerConfigProperty配置中拿到解析表达式进行页面数据的解析
 * <p>
 * help页面 和 target 页面
 * <p>
 * 对于博客页，HelpUrl是列表页，TargetUrl是文章页。
 * 对于论坛，HelpUrl是帖子列表，TargetUrl是帖子详情。
 * 对于电商网站，HelpUrl是分类列表，TargetUrl是商品详情。
 */
@Log4j2
public abstract class AbstractCrawlerPageProcessor extends AbstractProcessFlow implements PageProcessor {
    @Autowired
    private CrawlerHelper crawlerHelper;

    @Override
    public void handel(ProcessFlowData processFlowData) {
    }

    /**
     * 抽象处理类
     *
     * @param page
     */
    public abstract void handelPage(Page page);

    /**
     * 是否需要处理类型
     *
     * @return
     */
    public abstract boolean isNeedHandelType(String handelType);

    /**
     * 是否需要处理类型
     *
     * @return
     */
    public abstract boolean isNeedDocumentType(String documentType);

    /**
     * 获取 Site 信息
     *
     * @return
     */
    @Override
    public Site getSite() {
        Site site =
                Site.me().setRetryTimes(getRetryTimes()).setRetrySleepTime(getRetrySleepTime()).
                        setSleepTime(getSleepTime()).setTimeOut(getTimeOut());
        //header 配置
        Map<String, String> headerMap = getHeaderMap();
        if (null != headerMap && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                site.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return site;
    }

    /**
     * 添加数据到爬取列表
     * @param urlList      需要爬取的URL列表
     * @param request      上一个爬取的对象
     * @param documentType 需要处理的文档类型
     */
    public void addSpiderRequest(List<String> urlList, Request request,
                                 CrawlerEnum.DocumentType documentType) {
        if (null != urlList && !urlList.isEmpty()) {
            List<ParseItem> parseItemList = urlList.stream().map(url -> {
                CrawlerParseItem parseItem = new CrawlerParseItem();
                parseItem.setUrl(url);
                String handelType = crawlerHelper.getHandelType(request);
                parseItem.setDocumentType(documentType.name());
                parseItem.setHandelType(handelType);
                return parseItem;
            }).collect(Collectors.toList());
            addSpiderRequest(parseItemList);
        }
    }

    /**
     * 获取url列表
     *
     * @param helpParseRuleList
     * @return
     */
    public List<String> getHelpUrlList(List<ParseRule> helpParseRuleList) {
        List<String> helpUrlList = new ArrayList<String>();
        for (ParseRule parseRule : helpParseRuleList) {
            List<String> urlList =
                    ParseRuleUtils.getUrlLinks(parseRule.getParseContentList());
            helpUrlList.addAll(urlList);
        }
        return helpUrlList;
    }

    /**
     * 重试次数
     *
     * @return
     */
    public int getRetryTimes() {
        return 3;
    }

    /**
     * 重试间隔时间 ms
     *
     * @return
     */
    public int getRetrySleepTime() {
        return 1000;
    }

    /**
     * 抓取间隔时间
     *
     * @return
     */
    public int getSleepTime() {
        return 1000;
    }

    /**
     * 超时时间
     *
     * @return
     */
    public int getTimeOut() {
        return 10000;
    }

    /**
     * 该类的组件类型
     *
     * @return
     */
    @Override
    public CrawlerEnum.ComponentType getComponentType() {

        return CrawlerEnum.ComponentType.PAGEPROCESSOR;
    }

    @Resource
    private CrawlerPageProcessorManager crawlerPageProcessorManager;
    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     *
     * @param page
     */
    @Override
    public void process(Page page) {
        long currentTime = System.currentTimeMillis();
        String handelType = crawlerHelper.getHandelType(page.getRequest());
        log.info("开始解析数据页面，url:{},handelType:{}", page.getUrl(), handelType);
        crawlerPageProcessorManager.handel(page);
        log.info("解析数据页面完成，url:{},handelType:{},耗时：{}", page.getUrl(),
                handelType, System.currentTimeMillis() - currentTime);
    }
}
