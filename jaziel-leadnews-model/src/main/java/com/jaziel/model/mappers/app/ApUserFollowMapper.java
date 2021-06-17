package com.jaziel.model.mappers.app;

import com.jaziel.model.user.pojos.ApUserFollow;
import org.apache.ibatis.annotations.Param;

public interface ApUserFollowMapper {

    ApUserFollow selectByFollowId(@Param("burst") String burst, @Param("userId") Long userId, @Param("followId") Integer followId);

}