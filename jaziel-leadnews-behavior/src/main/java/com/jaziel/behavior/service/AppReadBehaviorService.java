package com.jaziel.behavior.service;

import com.jaziel.model.behavior.dtos.ReadBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/6/21 14:58
 */
public interface AppReadBehaviorService {

    ResponseResult saveReadBehavior(ReadBehaviorDto dto);
}
