package com.jaziel.model.crawler.core.callback;

import com.jaziel.model.crawler.core.proxy.CrawlerProxy;
import com.jaziel.model.crawler.core.proxy.CrawlerProxy;

import java.util.List;

/**
 * IP池更新回调
 */
public interface ProxyProviderCallBack {
    public List<CrawlerProxy> getProxyList();
}