package com.jaziel.behavior.service.impl;

import com.jaziel.behavior.service.AppUnlikesBehaviorService;
import com.jaziel.model.behavior.dtos.UnLikesBehaviorDto;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.behavior.pojos.ApUnlikesBehavior;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApBehaviorEntryMapper;
import com.jaziel.model.mappers.app.ApUnlikesBehaviorMapper;
import com.jaziel.model.user.pojos.ApUser;
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
public class AppUnlikesBehaviorServiceImpl implements AppUnlikesBehaviorService {
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApUnlikesBehaviorMapper apUnlikesBehaviorMapper;

    @Override
    public ResponseResult saveUnlikeBehavior(UnLikesBehaviorDto dto) {
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
        ApUnlikesBehavior alb = new ApUnlikesBehavior();
        alb.setEntryId(entry.getId());
        alb.setCreatedTime(new Date());
        alb.setArticleId(dto.getArticleId());
        alb.setType(dto.getType());
        int insert = apUnlikesBehaviorMapper.insert(alb);
        return ResponseResult.okResult(insert);
    }
}
