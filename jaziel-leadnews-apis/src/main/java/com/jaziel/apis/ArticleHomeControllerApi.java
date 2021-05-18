package com.jaziel.apis;

import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/5/18 19:15
 */
public interface ArticleHomeControllerApi {
    // 加载首页文章
    ResponseResult load(ArticleHomeDto dto);

    // 加载跟多文章
    ResponseResult loadMore(ArticleHomeDto dto);

    // 加载新的文章
    ResponseResult loadNew(ArticleHomeDto dto);
}
