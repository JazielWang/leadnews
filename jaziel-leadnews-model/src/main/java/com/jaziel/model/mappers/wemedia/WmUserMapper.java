package com.jaziel.model.mappers.wemedia;

import com.jaziel.model.media.pojos.WmUser;

public interface WmUserMapper {
    /**
     * 根据用户名称查询信息
     * @param name name
     * @return 粉丝对象
     */
    WmUser selectByName(String name);

    WmUser selectById(Long id);
}