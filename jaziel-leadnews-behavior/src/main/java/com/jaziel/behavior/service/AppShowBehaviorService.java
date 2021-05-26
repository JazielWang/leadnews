package com.jaziel.behavior.service;

import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/5/26 19:49
 */
public interface AppShowBehaviorService {
    /**
     * 存储行为数据
     * @param dto 行为实体
     * @return 存储结果
     */
    ResponseResult saveShowBehavior(ShowBehaviorDto dto);
}
