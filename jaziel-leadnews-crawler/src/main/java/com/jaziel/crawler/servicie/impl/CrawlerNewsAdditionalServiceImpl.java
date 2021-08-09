package com.jaziel.crawler.servicie.impl;

import com.jaziel.crawler.servicie.CrawlerNewsAdditionalService;
import com.jaziel.model.crawler.core.parse.ParseItem;
import com.jaziel.model.crawler.core.parse.impl.CrawlerParseItem;
import com.jaziel.model.crawler.pojos.ClNewsAdditional;
import com.jaziel.model.mappers.crawerls.ClNewsAdditionalMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/8 18:09
 */
@Service
@SuppressWarnings("all")
public class CrawlerNewsAdditionalServiceImpl implements CrawlerNewsAdditionalService {

    @Autowired
    private ClNewsAdditionalMapper clNewsAdditionalMapper;

    @Override
    public void saveAdditional(ClNewsAdditional clNewsAdditional) {
        if (clNewsAdditional != null) {
            clNewsAdditionalMapper.insert(clNewsAdditional);
        }
    }

    @Override
    public List<ClNewsAdditional> queryListByNeedUpdate(Date currentDate) {
        return clNewsAdditionalMapper.selectListByNeedUpdate(currentDate);
    }

    @Override
    public List<ClNewsAdditional> queryList(ClNewsAdditional clNewsAdditional) {
        List<ClNewsAdditional> clNewsAdditionals = clNewsAdditionalMapper.selectList(clNewsAdditional);
        return clNewsAdditionals;
    }

    @Override
    public boolean checkExist(String url) {
        Boolean isExist = false;
        if (url != null && StringUtils.isNotEmpty(url)) {
            ClNewsAdditional clNewsAdditional = getAdditionalByUrl(url);
            if (clNewsAdditional != null) {
                isExist = true;
            }
        }
        return isExist;
    }

    @Override
    public ClNewsAdditional getAdditionalByUrl(String url) {
        ClNewsAdditional clNewsAdditional = new ClNewsAdditional();
        clNewsAdditional.setUrl(url);
        List<ClNewsAdditional> additionalList = queryList(clNewsAdditional);
        if (null != additionalList && !additionalList.isEmpty()) {
            return additionalList.get(0);
        }
        return null;
    }

    @Override
    public boolean isExistsUrl(String url) {
        return checkExist(url);
    }

    @Override
    public void updateAdditional(ClNewsAdditional clNewsAdditional) {
        clNewsAdditionalMapper.updateByPrimaryKeySelective(clNewsAdditional);
    }

    @Override
    public List<ParseItem> toParseItem(List<ClNewsAdditional> additionalList) {
        List<ParseItem> parseItems = new ArrayList<>();
        if (!additionalList.isEmpty() && additionalList != null){
            for (ClNewsAdditional clNewsAdditional : additionalList) {
                ParseItem parseItem = toParseItem(clNewsAdditional);
                if (parseItem != null){
                    parseItems.add(parseItem);
                }
            }
        }
        return parseItems;
    }

    private ParseItem toParseItem(ClNewsAdditional additional) {
        CrawlerParseItem crawlerParseItem = null;
        if (null != additional) {
            crawlerParseItem = new CrawlerParseItem();
            crawlerParseItem.setUrl(additional.getUrl());
        }
        return crawlerParseItem;
    }
    /**
     * 获取增量统计数据
     * @return
     */
    @Override
    public List<ParseItem> queryIncrementParseItem(Date currentDate) {
        List<ClNewsAdditional> clNewsAdditionalList =
                queryListByNeedUpdate(currentDate);
        List<ParseItem> parseItemList = toParseItem(clNewsAdditionalList);
        return parseItemList;
    }
}
