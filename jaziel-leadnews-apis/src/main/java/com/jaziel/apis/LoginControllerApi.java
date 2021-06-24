package com.jaziel.apis;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.pojos.ApUser;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 王杰
 * @date 2021/6/24 15:15
 */
public interface LoginControllerApi {
    ResponseResult login(@RequestBody ApUser user);
}
