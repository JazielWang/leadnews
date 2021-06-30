package com.jaziel.behavior.controller.v1;

import com.jaziel.apis.behavior.BehaviorControllerApi;
import com.jaziel.behavior.service.AppLikesBehaviorService;
import com.jaziel.behavior.service.AppReadBehaviorService;
import com.jaziel.behavior.service.AppShowBehaviorService;
import com.jaziel.behavior.service.AppUnlikesBehaviorService;
import com.jaziel.model.behavior.dtos.LikesBehaviorDto;
import com.jaziel.model.behavior.dtos.ReadBehaviorDto;
import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.behavior.dtos.UnLikesBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private AppLikesBehaviorService appLikesBehaviorService;

    @Autowired
    private AppUnlikesBehaviorService appUnlikesBehaviorService;

    @Autowired
    private AppReadBehaviorService appReadBehaviorService;

    @Override
    @RequestMapping("/show_behavior")
    public ResponseResult saveShowBehavior(@RequestBody ShowBehaviorDto dto) {
        return appShowBehaviorService.saveShowBehavior(dto);
    }

    @Override
    @PostMapping("/like_behavior")
    public ResponseResult saveLikesBehavior(@RequestBody LikesBehaviorDto dto) {
        return appLikesBehaviorService.saveLikeBehavior(dto);
    }

    @Override
    @PostMapping("/unlike_behavior")
    public ResponseResult saveUnlikesBehavior(@RequestBody UnLikesBehaviorDto dto) {
        return appUnlikesBehaviorService.saveUnlikeBehavior(dto);
    }

    @Override
    @PostMapping("/read_behavior")
    public ResponseResult saveReadBehavior(@RequestBody ReadBehaviorDto dto) {
        return appReadBehaviorService.saveReadBehavior(dto);
    }
}
