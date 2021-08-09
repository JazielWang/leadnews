package com.jaziel.crawler.servicie.impl;

import com.jaziel.crawler.servicie.CrawlerNewsService;
import com.jaziel.model.crawler.pojos.ClNews;
import com.jaziel.model.mappers.crawerls.ClNewsMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/9 14:42
 */
public class CrawlerNewsServiceImpl implements CrawlerNewsService {
    @Autowired
    private ClNewsMapper clNewsMapper;

    @Override
    public void saveNews(ClNews clNews) {
        clNewsMapper.insertSelective(clNews);
    }

    @Override
    public void deleteByUrl(String url) {
        clNewsMapper.deleteByUrl(url);
    }

    @Override
    public List<ClNews> queryList(ClNews clNews) {
        return clNewsMapper.selectList(clNews);
    }

    @Override
    public void updateNews(ClNews clNews) {
        clNewsMapper.updateByPrimaryKey(clNews);
    }
}
