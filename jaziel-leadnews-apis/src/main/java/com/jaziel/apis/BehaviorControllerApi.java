package com.jaziel.apis;

import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/5/26 19:40
 */
public interface BehaviorControllerApi {
    /**
     * 保存用户行为
     * @param dto
     * @return
     */
    ResponseResult saveShowBehavior(ShowBehaviorDto dto);
}
