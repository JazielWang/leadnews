package com.jaziel.model.mappers.app;

import com.jaziel.model.article.pojos.ApCollection;
import org.springframework.data.repository.query.Param;

public interface ApCollectionMapper {
    /**
     * 选择一个终端的收藏数据
     *
     * @return
     */
    ApCollection selectForEntryId(@Param("burst") String burst, @Param("objectId")Integer objectId, @Param("entryId")Integer entryId, @Param("type")Short type);
}