package com.jaziel.model.mappers.app;

import com.jaziel.model.user.pojos.ApUserFan;
import org.apache.ibatis.annotations.Param;

public interface ApUserFanMapper {
    int insert(ApUserFan record);

    ApUserFan selectByFansId(@Param("burst") String burst, @Param("userId") Integer userId, @Param("fansId") Long fansId);

    int deleteByFansId(String burst, Integer userId, Long fansId);
}