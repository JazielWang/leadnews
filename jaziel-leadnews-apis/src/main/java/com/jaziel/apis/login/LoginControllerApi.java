package com.jaziel.apis.login;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.pojos.ApUser;

/**
 * @author 王杰
 * @date 2021/6/24 15:15
 */
public interface LoginControllerApi {
    ResponseResult login(ApUser user);
}
