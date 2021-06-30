package com.jaziel.apis.madia;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.pojos.WmUser;

public interface LoginControllerApi {
    public ResponseResult login(WmUser user);
}