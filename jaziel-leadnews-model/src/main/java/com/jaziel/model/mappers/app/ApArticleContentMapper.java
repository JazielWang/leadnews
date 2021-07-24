package com.jaziel.model.mappers.app;

import com.jaziel.model.article.pojos.ApArticleContent;

/**
 * @author 王杰
 * @date 2021/6/13 20:12
 */
public interface ApArticleContentMapper {
    void insert(ApArticleContent apArticleContent);
    ApArticleContent selectByArticleId(Integer articleId);
}
