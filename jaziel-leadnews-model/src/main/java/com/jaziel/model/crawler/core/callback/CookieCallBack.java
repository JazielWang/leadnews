package com.jaziel.model.crawler.core.callback;


import com.jaziel.model.crawler.core.cookie.CrawlerCookie;

public interface CookieCallBack {
    /**
     * 获取CookieMap
     *
     * @return
     */
    public CrawlerCookie getCookieEntity(String url);


}
