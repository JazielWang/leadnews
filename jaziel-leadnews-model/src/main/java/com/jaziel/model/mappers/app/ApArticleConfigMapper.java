package com.jaziel.model.mappers.app;

import com.jaziel.model.article.pojos.ApArticleConfig;

/**
 * @author 王杰
 * @date 2021/6/13 20:23
 */
public interface ApArticleConfigMapper {
    ApArticleConfig selectByArticleId(Integer articleId);
}
