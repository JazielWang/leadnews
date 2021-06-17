package com.jaziel.model.mappers.app;

import com.jaziel.model.behavior.pojos.ApUnlikesBehavior;
import org.apache.ibatis.annotations.Param;

/**
 * @author 王杰
 * @date 2021/6/14 15:54
 */
public interface ApUnlikesBehaviorMapper {
    /**
     * 选择最后一条不喜欢数据
     * @return
     */
    ApUnlikesBehavior selectLastUnLike(@Param("entryId") Integer entryId, @Param("articleId") Integer articleId);
}
