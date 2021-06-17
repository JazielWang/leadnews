package com.jaziel.article.service;

import com.jaziel.model.article.dtos.ArticleInfoDto;
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

    /**
     * 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢、阅读位置等
     * @param dto 文章信息
     * @return
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}
