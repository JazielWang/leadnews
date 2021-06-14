package com.jaziel.apis;

import com.jaziel.model.article.dtos.ArticleInfoDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/6/13 20:40
 */
public interface ArticleInfoControllerApi {
    /**
     * 加载首页详情
     * @param dto 文章信息
     * @return
     */
    ResponseResult loadArticleInfo(ArticleInfoDto dto);
    /**
     * 加载文章详情的行为内容
     * @param dto 文章信息
     * @return
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}
