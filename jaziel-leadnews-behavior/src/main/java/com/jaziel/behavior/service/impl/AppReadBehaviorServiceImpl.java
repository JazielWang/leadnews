package com.jaziel.behavior.service.impl;

import com.jaziel.behavior.service.AppReadBehaviorService;
import com.jaziel.common.zookeeper.sequence.Sequences;
import com.jaziel.model.behavior.dtos.ReadBehaviorDto;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.behavior.pojos.ApReadBehavior;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApBehaviorEntryMapper;
import com.jaziel.model.mappers.app.ApReadBehaviorMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.utils.common.BurstUtils;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 王杰
 * @date 2021/6/21 15:01
 */
@Service
@SuppressWarnings("all")
public class AppReadBehaviorServiceImpl implements AppReadBehaviorService {
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApReadBehaviorMapper apReadBehaviorMapper;

    @Autowired
    private Sequences sequences;

    @Override
    public ResponseResult saveReadBehavior(ReadBehaviorDto dto) {
        //获取用户信息，获取设备id
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null && dto.getEquipmentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "用户和设备不能同时为空");
        }
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        ApBehaviorEntry entry = apBehaviorEntryMapper.findByUserIdOrEquipment(userId, dto.getEquipmentId());
        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if (entry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApReadBehavior alb = apReadBehaviorMapper.selectByEntryId(BurstUtils.groudOne(entry.getId()), entry.getId(), dto.getArticleId());
        boolean isInsert = false;
        if (alb == null) {
            alb = new ApReadBehavior();
            alb.setId(sequences.sequenceApReadBehavior());
            alb.setCreatedTime(new Date());
            isInsert = true;
        }
        alb.setEntryId(entry.getId());
        alb.setCount(dto.getCount());
        alb.setPercentage(dto.getPercentage());
        alb.setArticleId(dto.getArticleId());
        alb.setLoadDuration(dto.getLoadDuration());
        alb.setReadDuration(dto.getReadDuration());
        alb.setUpdatedTime(new Date());
        alb.setBurst(BurstUtils.encrypt(alb.getId(), alb.getEntryId()));
        int count = 0;
        if (isInsert) {
            count = apReadBehaviorMapper.insert(alb);
        } else {
            count = apReadBehaviorMapper.update(alb);
        }
        return ResponseResult.okResult(count);
    }
}
