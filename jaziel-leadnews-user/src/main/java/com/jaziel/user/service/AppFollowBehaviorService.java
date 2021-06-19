package com.jaziel.user.service;

import com.jaziel.model.behavior.dtos.FollowBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/6/19 15:55
 */
public interface AppFollowBehaviorService {
    /**
     * 存储关注信息
     * @return
     */
    ResponseResult saveFollowBehavior(FollowBehaviorDto dto);
}
