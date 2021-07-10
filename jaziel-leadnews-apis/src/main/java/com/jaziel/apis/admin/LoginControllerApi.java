package com.jaziel.apis.admin;

import com.jaziel.model.admin.pojos.AdUser;
import com.jaziel.model.common.dtos.ResponseResult;

public interface LoginControllerApi {
    public ResponseResult login(AdUser user);
}