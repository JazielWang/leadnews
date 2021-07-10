package com.jaziel.admin.service;

import com.jaziel.model.admin.dtos.CommonDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @Author Jaziel
 * @date 2021/7/10 17:52
 */
public interface CommonService {
    /**
     * 加载通用的数据列表
     * @param dto
     * @return
     */
    ResponseResult list(CommonDto dto);
    /**
     * 修改通用的数据列表
     * @param dto
     * @return
     */
    ResponseResult update(CommonDto dto);
    /**
     * 删除通用的数据列表
     * @param dto
     * @return
     */
    ResponseResult delete(CommonDto dto);
}
