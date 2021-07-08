package com.jaziel.media.service.impl;

import com.jaziel.media.service.AdChannelService;
import com.jaziel.model.admin.pojos.AdChannel;
import com.jaziel.model.mappers.admin.AdChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/7/8 15:38
 */
@Service
@SuppressWarnings("all")
public class AdChannelServiceImpl implements AdChannelService{

    @Autowired
    private AdChannelMapper adChannelMapper;

    @Override
    public List<AdChannel> selectAll() {
        return adChannelMapper.selectAll();
    }
}
