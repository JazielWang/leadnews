package com.jaziel.user.service;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.dtos.UserRelationDto;

public interface AppUserRelationService {
    /**
     * 关注动作
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);
}