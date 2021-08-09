package com.jaziel.crawler.servicie;

import com.jaziel.model.crawler.pojos.ClNews;

import java.util.List;

public interface CrawlerNewsService {
    public void saveNews(ClNews clNews);

    public void updateNews(ClNews clNews);

    public void deleteByUrl(String url);

    public List<ClNews> queryList(ClNews clNews);
}