package com.jaziel.model.mappers.app;

import com.jaziel.model.behavior.pojos.ApShowBehavior;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/5/26 20:22
 */
public interface ApShowBehaviorMapper {
    List<ApShowBehavior> findListByEntryIdAndArticleIds(@Param("entryId") Integer entryId, @Param("articleIds") Integer[] articleIds);

    void saveShowBehavior(@Param("articleIds") Integer[] articleIds, @Param("entryId") Integer entryId);
}
