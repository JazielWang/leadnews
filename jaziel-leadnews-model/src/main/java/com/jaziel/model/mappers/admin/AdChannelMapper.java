package com.jaziel.model.mappers.admin;

import com.jaziel.model.admin.pojos.AdChannel;

import java.util.List;

public interface AdChannelMapper {
    /**
     * 查询所有
     */
    public List<AdChannel> selectAll();
    AdChannel selectByPrimaryKey(Integer id);
}