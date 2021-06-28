package com.jaziel.model.mappers.app;

import com.jaziel.model.article.pojos.ApHotWords;

import java.util.List;

public interface ApHotWordsMapper {
    /**
     * 查询今日热词
     * @param hotDate 日期
     * @return 热词清单
     */
    List<ApHotWords> queryByHotDate(String hotDate);
}