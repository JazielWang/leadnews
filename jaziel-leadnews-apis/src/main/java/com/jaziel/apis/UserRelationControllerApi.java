package com.jaziel.apis;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.dtos.UserRelationDto;

/**
 * 关注
 * @author 王杰
 */
public interface UserRelationControllerApi {

    ResponseResult follow(UserRelationDto dto);
}