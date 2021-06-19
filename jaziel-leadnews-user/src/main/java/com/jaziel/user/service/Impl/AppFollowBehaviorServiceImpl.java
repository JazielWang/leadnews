package com.jaziel.user.service.Impl;

import com.jaziel.common.zookeeper.sequence.Sequences;
import com.jaziel.model.behavior.dtos.FollowBehaviorDto;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.behavior.pojos.ApFollowBehavior;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApBehaviorEntryMapper;
import com.jaziel.model.mappers.app.ApFollowBehaviorMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.user.service.AppFollowBehaviorService;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 王杰
 * @date 2021/6/19 15:57
 */
@Service
@SuppressWarnings("all")
public class AppFollowBehaviorServiceImpl implements AppFollowBehaviorService {
    Logger logger = LoggerFactory.getLogger(AppFollowBehaviorServiceImpl.class);
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApFollowBehaviorMapper apFollowBehaviorMapper;

    @Autowired
    private Sequences sequences;

    @Override
    //@Async("async")
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto) {
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
        ApFollowBehavior apFollowBehavior = new ApFollowBehavior();
        apFollowBehavior.setEntryId(entry.getId());
        apFollowBehavior.setFollowId(dto.getFollowId());
        apFollowBehavior.setArticleId(dto.getArticleId());
        apFollowBehavior.setCreatedTime(new Date());
        return ResponseResult.okResult(apFollowBehaviorMapper.insert(apFollowBehavior));
    }
}
