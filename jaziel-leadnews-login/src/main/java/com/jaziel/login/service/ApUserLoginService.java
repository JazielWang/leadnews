package com.jaziel.login.service;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.pojos.ApUser;

/**
 * @author 王杰
 * @date 2021/6/24 15:04
 */
public interface ApUserLoginService {
    /**
     * 用户登录验证
     * @param user
     * @return
     */
    ResponseResult loginAuth(ApUser user);
}
