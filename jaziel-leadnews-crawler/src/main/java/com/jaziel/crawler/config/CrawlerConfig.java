package com.jaziel.crawler.config;

import com.jaziel.crawler.helper.CookieHelper;
import com.jaziel.crawler.helper.CrawlerHelper;
import com.jaziel.crawler.process.entity.CrawlerConfigProperty;
import com.jaziel.crawler.utils.SeleniumClient;
import com.jaziel.model.crawler.core.callback.DataValidateCallBack;
import com.jaziel.model.crawler.core.parse.ParseRule;
import com.jaziel.model.crawler.core.proxy.CrawlerProxyProvider;
import com.jaziel.model.crawler.enums.CrawlerEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王杰
 */
@Getter
@Setter
@Configuration
@Log4j2
@ConfigurationProperties(prefix = "crawler.init.url")
@PropertySource("classpath:crawler.properties")
public class CrawlerConfig {

    private String prefix;
    private String suffix;

    public List<String> getInitCrawlerUrlList() {
        List<String> urls = new ArrayList<>();
        if (suffix != null) {
            String[] split = suffix.split(",");
            if (split.length > 0) {
                for (String s : split) {
                    if (StringUtils.isNotEmpty(s)) {
                        urls.add(prefix + s);
                    }
                }
            }
        }
        return urls;
    }

    @Bean
    public SeleniumClient getSeleniumClient() {
        return new SeleniumClient();
    }

    @Value("${crux.cookie.name}")
    private String CRUX_COOKIE_NAME;

    /**
     * 设置Cookie辅助类
     *
     * @return
     */
    @Bean
    public CookieHelper getCookieHelper() {
        return new CookieHelper(CRUX_COOKIE_NAME);
    }

    /**
     * 数据校验匿名内部类
     *
     * @param cookieHelper
     * @return
     */
    private DataValidateCallBack getDataValidateCallBack(CookieHelper cookieHelper) {
        return new DataValidateCallBack() {
            @Override
            public boolean validate(String content) {
                boolean flag = true;
                if (StringUtils.isEmpty(content)) {
                    flag = false;
                } else {
                    boolean isContains_acw_sc_v2 =
                            content.contains("acw_sc__v2");
                    boolean isContains_location_reload =
                            content.contains("document.location.reload()");
                    if (isContains_acw_sc_v2 && isContains_location_reload) {
                        flag = false;
                    }
                }
                return flag;
            }
        };
    }

    /**
     * CrawerHelper 辅助类
     *
     * @return
     */
    @Bean
    public CrawlerHelper getCrawerHelper() {
        CookieHelper cookieHelper = getCookieHelper();
        CrawlerHelper crawerHelper = new CrawlerHelper();
        DataValidateCallBack dataValidateCallBack =
                getDataValidateCallBack(cookieHelper);
        crawerHelper.setDataValidateCallBack(dataValidateCallBack);
        return crawerHelper;
    }

    @Value("${proxy.isUsedProxyIp}")
    private Boolean isUsedProxyIp;

    /**
     * CrawlerProxyProvider bean
     *
     * @return
     */
    @Bean
    public CrawlerProxyProvider getCrawlerProxyProvider() {
        CrawlerProxyProvider crawlerProxyProvider = new CrawlerProxyProvider();
        crawlerProxyProvider.setUsedProxyIp(isUsedProxyIp);
        return crawlerProxyProvider;
    }

    @Value("${crawler.help.nextPagingSize}")
    private Integer crawlerHelpNextPagingSize;

    private String initCrawlerXpath = "//ul[@class='feedlist_mod']/li[@class='clearfix']/div[@class='list_con']/dl[@class='list_userbar']/dd[@class='name']/a";

    private String helpCrawlerXpath = "//div[@class='articleMeList-integration']/div[@class='article-list']/div[@class='article-item-box']/h4/a";

    @Bean
    public CrawlerConfigProperty getCrawlerConfigProperty() {
        CrawlerConfigProperty crawlerConfigProperty = new CrawlerConfigProperty();
        crawlerConfigProperty.setInitCrawlerUrlList(getInitCrawlerUrlList());
        // 帮助页面抓取的Xpath
        crawlerConfigProperty.setHelpCrawlerXpath(helpCrawlerXpath);
        // 文章页面抓取的Xpath
        crawlerConfigProperty.setTargetParseRuleList(getTargetParseRuleList());
        // 是否开启帮助页面分页抓取
        crawlerConfigProperty.setCrawlerHelpNextPagingSize(crawlerHelpNextPagingSize);
        // 初始化抓取的Xpath
        crawlerConfigProperty.setInitCrawlerXpath(initCrawlerXpath);
        return crawlerConfigProperty;
    }

    private List<ParseRule> getTargetParseRuleList() {
        List<ParseRule> list = new ArrayList<ParseRule>() {{
            //标题
            add(new ParseRule("title", CrawlerEnum.ParseRuleType.XPATH,
                    "//h1[@class='title-article']/text()"));
            //作者
            add(new ParseRule("author", CrawlerEnum.ParseRuleType.XPATH,
                    "//a[@class='follow-nickName']/text()"));
            //发布日期
            add(new ParseRule("releaseDate", CrawlerEnum.ParseRuleType.XPATH,
                    "//span[@class='time']/text()"));
            //标签
            add(new ParseRule("labels", CrawlerEnum.ParseRuleType.XPATH,
                    "//span[@class='tags-box']/a/text()"));
            //个人空间
            add(new ParseRule("personalSpace", CrawlerEnum.ParseRuleType.XPATH,
                    "//a[@class='follow-nickName']/@href"));
            //阅读量
            add(new ParseRule("readCount", CrawlerEnum.ParseRuleType.XPATH,
                    "//span[@class='read-count']/text()"));
            //点赞量
            add(new ParseRule("likes", CrawlerEnum.ParseRuleType.XPATH,
                    "//div[@class='toolbox-middle']/ul[@class='toolbox-list']/li[@class='tool-item tool-item-size tool-active is-like']/a[@class='tool-item-href']/span/text()"));
            //回复次数
            add(new ParseRule("commentCount", CrawlerEnum.ParseRuleType.XPATH,
                    "//div[@class='toolbox-middle']/ul[@class='toolbox-list']/li[@class='tool-item tool-item-size tool-active tool-item-comment']/a[@class='tool-item-href']/span/text()"));
            //html内容
            add(new ParseRule("content", CrawlerEnum.ParseRuleType.XPATH,
                    "//div[@id='content_views']/html()"));
        }};
        return list;
    }

    private Spider spider;

    public Spider getSpider() {
        return spider;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }
}
