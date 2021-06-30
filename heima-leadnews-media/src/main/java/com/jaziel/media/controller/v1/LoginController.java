package com.jaziel.media.controller.v1;

import com.jaziel.apis.madia.LoginControllerApi;
import com.jaziel.media.service.UserLoginService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.pojos.WmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/6/28 20:26
 */
@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    @RequestMapping("/in")
    public ResponseResult login(WmUser user) {
        return userLoginService.login(user);
    }
}
