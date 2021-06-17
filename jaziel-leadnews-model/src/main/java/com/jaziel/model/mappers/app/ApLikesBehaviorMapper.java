package com.jaziel.model.mappers.app;

import com.jaziel.model.behavior.pojos.ApLikesBehavior;
import org.apache.ibatis.annotations.Param;

/**
 * @author 王杰
 * @date 2021/6/14 15:52
 */
public interface ApLikesBehaviorMapper {
    /**
     * 选择最后一条喜欢按钮
     *
     * @return
     */
    ApLikesBehavior selectLastLike(@Param("burst") String burst, @Param("objectId") Integer objectId, @Param("entryId") Integer entryId, @Param("type") Short type);
}
