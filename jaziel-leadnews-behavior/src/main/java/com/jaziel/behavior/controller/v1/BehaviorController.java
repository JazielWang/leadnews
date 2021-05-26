package com.jaziel.behavior.controller.v1;

import com.jaziel.apis.BehaviorControllerApi;
import com.jaziel.behavior.service.AppShowBehaviorService;
import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/5/26 19:43
 */
@RestController
@RequestMapping("/api/v1/behavior")
public class BehaviorController implements BehaviorControllerApi {
    @Autowired
    private AppShowBehaviorService appShowBehaviorService;

    @Override
    @GetMapping("/show_behavior")
    public ResponseResult saveShowBehavior(ShowBehaviorDto dto) {
        return appShowBehaviorService.saveShowBehavior(dto);
    }
}
