package com.jaziel.behavior.service.impl;

import com.jaziel.behavior.service.AppLikesBehaviorService;
import com.jaziel.common.zookeeper.sequence.Sequences;
import com.jaziel.model.behavior.dtos.LikesBehaviorDto;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.behavior.pojos.ApLikesBehavior;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApBehaviorEntryMapper;
import com.jaziel.model.mappers.app.ApLikesBehaviorMapper;
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
public class AppLikesBehaviorServiceImpl implements AppLikesBehaviorService {
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;

    @Autowired
    private Sequences sequences;

    @Override
    public ResponseResult saveLikeBehavior(LikesBehaviorDto dto) {
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
        ApLikesBehavior apLikesBehavior = new ApLikesBehavior();
        apLikesBehavior.setId(sequences.sequenceApLikes());
        apLikesBehavior.setCreatedTime(new Date());
        apLikesBehavior.setType(dto.getType());
        apLikesBehavior.setEntryId(dto.getEntryId());
        apLikesBehavior.setOperation(dto.getOperation());
        apLikesBehavior.setBehaviorEntryId(entry.getId());
        apLikesBehavior.setBurst(BurstUtils.encrypt(apLikesBehavior.getId(), apLikesBehavior.getBehaviorEntryId()));
        int insert = apLikesBehaviorMapper.insert(apLikesBehavior);
        return ResponseResult.okResult(insert);
    }
}
