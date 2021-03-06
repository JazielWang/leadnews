package com.jaziel.model.mappers.app;

import com.jaziel.model.article.pojos.ApAssociateWords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApAssociateWordsMapper {
    /**
     * 根据关键词查询联想词
     * @param searchWords
     * @return
     */
    List<ApAssociateWords> selectByAssociateWords(@Param("searchWords") String searchWords, @Param("limit") int limit);
}