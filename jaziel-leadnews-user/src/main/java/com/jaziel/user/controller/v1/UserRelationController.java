package com.jaziel.user.controller.v1;

import com.jaziel.apis.user.UserRelationControllerApi;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.dtos.UserRelationDto;
import com.jaziel.user.service.AppUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/6/19 18:09
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserRelationController implements UserRelationControllerApi {
    @Autowired
    private AppUserRelationService appUserRelationService;

    @Override
    @PostMapping("/user_follow")
    public ResponseResult follow(@RequestBody UserRelationDto dto) {
        return appUserRelationService.follow(dto);
    }
}
