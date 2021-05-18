package com.jaziel.article.service;

import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @author 王杰
 * @date 2021/5/18 19:37
 */
public interface AppArticleService {
    ResponseResult load(Short loadtypeLoadMore, ArticleHomeDto dto);
}
