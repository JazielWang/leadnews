package com.jaziel.user.service.Impl;

import com.jaziel.common.zookeeper.sequence.Sequences;
import com.jaziel.model.article.pojos.ApAuthor;
import com.jaziel.model.behavior.dtos.FollowBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.*;
import com.jaziel.model.user.dtos.UserRelationDto;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.model.user.pojos.ApUserFan;
import com.jaziel.model.user.pojos.ApUserFollow;
import com.jaziel.user.service.AppFollowBehaviorService;
import com.jaziel.user.service.AppUserRelationService;
import com.jaziel.utils.common.BurstUtils;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 王杰
 * @date 2021/6/19 16:29
 */
@Service
@SuppressWarnings("all")
public class AppUserRelationServiceImpl implements AppUserRelationService {
    Logger logger = LoggerFactory.getLogger(AppUserRelationServiceImpl.class);

    @Autowired
    private ApAuthorMapper apAuthorMapper;
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private ApUserFollowMapper apUserFollowMapper;
    @Autowired
    private ApUserFanMapper apUserFanMapper;
    @Autowired
    private AppFollowBehaviorService appFollowBehaviorService;
    @Autowired
    private Sequences sequences;


    @Override
    public ResponseResult follow(UserRelationDto dto) {
        if (dto.getOperation() == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "无效的operation参数");
        }
        Integer followId = dto.getUserId();
        if (followId == null && dto.getAuthorId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "followId或authorId不能为空");
        } else if (followId == null) {
            ApAuthor author = apAuthorMapper.selectById(dto.getAuthorId());
            if (author != null) {
                followId = author.getUserId().intValue();
            }
        }
        if (followId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "关注人不存在");
        } else {
            ApUser user = AppThreadLocalUtils.getUser();
            if (user != null) {
                // 关注操作
                if (dto.getOperation() == 0) {
                    return followUserId(user, followId, dto.getArticleId());
                } else {
                    return followCancelUserId(user, followId);
                }
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
        }
    }

    /**
     * 处理取消关注逻辑
     *
     * @param user     用户
     * @param followId 要取消关注的人
     * @return
     */
    private ResponseResult followCancelUserId(ApUser user, Integer followId) {
        ApUserFollow follow = apUserFollowMapper.selectByFollowId(BurstUtils.groudOne(user.getId()), user.getId(), followId);
        if (follow == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "未关注");
        } else {
            ApUserFan apUserFan = apUserFanMapper.selectByFansId(BurstUtils.groudOne(followId), followId, user.getId());
            if (apUserFan != null) {
                apUserFanMapper.deleteByFansId(BurstUtils.groudOne(followId), followId, user.getId());
            }
            return ResponseResult.okResult(apUserFollowMapper.deleteByFollowId(BurstUtils.groudOne(user.getId()), user.getId(), followId));
        }
    }

    /**
     * 处理关注逻辑
     *
     * @param user      用户
     * @param followId  要关注的人
     * @param articleId 文章id
     * @return
     */
    private ResponseResult followUserId(ApUser user, Integer followId, Integer articleId) {
        ApUser apUser = apUserMapper.selectById(followId);
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "关注人不存在");
        }
        ApUserFollow follow = apUserFollowMapper.selectByFollowId(BurstUtils.groudOne(user.getId()), user.getId(), followId);
        if (follow == null) {
            ApUserFan fan = apUserFanMapper.selectByFansId(BurstUtils.groudOne(followId), followId, user.getId());
            if (fan == null) {
                fan = new ApUserFan();
                fan.setId(sequences.sequenceApUserFan());
                fan.setUserId(followId);
                fan.setFansId(user.getId());
                fan.setFansName(user.getName());
                fan.setCreatedTime(new Date());
                fan.setLevel((short) 0);
                fan.setIsDisplay(true);
                fan.setIsShieldComment(false);
                fan.setIsShieldLetter(false);
                fan.setBurst(BurstUtils.encrypt(fan.getId(), fan.getUserId()));
                apUserFanMapper.insert(fan);
            }
            follow = new ApUserFollow();
            follow.setId(sequences.sequenceApUserFollow());
            follow.setUserId(user.getId());
            follow.setFollowId(followId);
            follow.setFollowName(apUser.getName());
            follow.setCreatedTime(new Date());
            follow.setLevel((short) 0);
            follow.setIsNotice(true);
            follow.setBurst(BurstUtils.encrypt(follow.getId(), follow.getUserId()));
            // 记录关注行为
            FollowBehaviorDto followBehaviorDto = new FollowBehaviorDto();
            followBehaviorDto.setFollowId(followId);
            followBehaviorDto.setArticleId(articleId);
            appFollowBehaviorService.saveFollowBehavior(followBehaviorDto);

            return ResponseResult.okResult(apUserFollowMapper.insert(follow));
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "已关注");
        }
    }
}
