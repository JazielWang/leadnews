package com.jaziel.admin.service;

import com.jaziel.model.admin.pojos.AdUser;
import com.jaziel.model.common.dtos.ResponseResult;

public interface UserLoginService {
    ResponseResult login(AdUser user);
}