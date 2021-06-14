package com.jaziel.article.service;

import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/6/13 20:28
 */
public interface AppArticleInfoService {
    /**
     * 加载文章详情内容
     * @param ArticleId 文章ID
     * @return 消息
     */
    ResponseResult getArticleInfo(Integer ArticleId);
}
