package com.jaziel.media.service;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.pojos.WmUser;

public interface UserLoginService {
    /**
     * 登录接口
     * @param user 用户对象
     * @return 登录结果
     */
    ResponseResult login(WmUser user);
}