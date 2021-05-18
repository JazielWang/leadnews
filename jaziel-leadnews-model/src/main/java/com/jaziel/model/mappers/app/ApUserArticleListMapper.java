package com.jaziel.model.mappers.app;

import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.model.user.pojos.ApUserArticleList;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/5/18 20:22
 */
public interface ApUserArticleListMapper {
    List<ApUserArticleList> loadArticleIdListByUser(@Param("user") ApUser user, @Param("type")Short type, @Param("dto")ArticleHomeDto dto);
}
