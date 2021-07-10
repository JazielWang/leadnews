package com.jaziel.admin.controller.v1;

import com.jaziel.admin.service.UserLoginService;
import com.jaziel.apis.admin.LoginControllerApi;
import com.jaziel.model.admin.pojos.AdUser;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/7/9 16:50
 */
@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    @RequestMapping("/in")
    public ResponseResult login(@RequestBody AdUser user) {
        return userLoginService.login(user);
    }
}
