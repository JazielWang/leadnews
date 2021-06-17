package com.jaziel.article.service.impl;

import com.jaziel.article.service.AppArticleInfoService;
import com.jaziel.model.article.dtos.ArticleInfoDto;
import com.jaziel.model.article.pojos.ApArticleConfig;
import com.jaziel.model.article.pojos.ApArticleContent;
import com.jaziel.model.article.pojos.ApAuthor;
import com.jaziel.model.article.pojos.ApCollection;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.behavior.pojos.ApLikesBehavior;
import com.jaziel.model.behavior.pojos.ApUnlikesBehavior;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.*;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.model.user.pojos.ApUserFollow;
import com.jaziel.utils.common.BurstUtils;
import com.jaziel.utils.common.ZipUtils;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/6/13 20:30
 */
@Service
@SuppressWarnings("all")
public class AppArticleInfoServiceImpl implements AppArticleInfoService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Override
    public ResponseResult getArticleInfo(Integer ArticleId) {
        if (ArticleId == null || ArticleId < 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApArticleConfig apArticleConfig = apArticleConfigMapper.selectByArticleId(ArticleId);
        Map<String, Object> data = new HashMap<>();
        if (apArticleConfig == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        } else if (!apArticleConfig.getIsDelete()) {
            // 没删除的标识才返回给客户端
            ApArticleContent apArticleContent = apArticleContentMapper.selectByArticleId(ArticleId);
            String gunzip = ZipUtils.gunzip(apArticleContent.getContent());
            apArticleContent.setContent(gunzip);
            data.put("content", apArticleContent);
        }
        data.put("config", apArticleConfig);
        return ResponseResult.okResult(data);
    }

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;
    @Autowired
    private ApUnlikesBehaviorMapper apUnlikesBehaviorMapper;
    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;
    @Autowired
    private ApCollectionMapper apCollectionMapper;
    @Autowired
    private ApUserFollowMapper apUserFollowMapper;
    @Autowired
    private ApAuthorMapper apAuthorMapper;

    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        // 用户和设备不能同时为空
        if (user == null && dto.getEquipmentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //1，通过equipmentId或用户id查看行为实体  --->entryId
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        ApBehaviorEntry entry = apBehaviorEntryMapper.findByUserIdOrEquipment(userId, dto.getEquipmentId());
        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if (entry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        boolean isUnLike = false, isLike = false, isCollection = false, isFollow = false;
        String burst = BurstUtils.groudOne(entry.getId());
        // 1. 判断是否是已经不喜欢
        ApUnlikesBehavior apUnlikesBehavior = apUnlikesBehaviorMapper.selectLastUnLike(entry.getId(), dto.getArticleId());
        if (apUnlikesBehavior != null) {
            isUnLike = true;
        }
        //2，通过entryId和articleId去查看收藏表，看是否有数据，有数据就说明已经收藏，否则就是没有收藏
        ApCollection apCollection = apCollectionMapper.selectForEntryId(burst, entry.getId(), dto.getArticleId(), ApCollection.Type.ARTICLE.getCode());
        if (apCollection != null) {
            isCollection = true;
        }
        // 3，通过entryId和articleId去查看点赞表，看是否有数据，有数据就说明已经点赞，否则就是没有点赞
        ApLikesBehavior apLikesBehavior = apLikesBehaviorMapper.selectLastLike(burst, entry.getId(), dto.getArticleId(), ApCollection.Type.ARTICLE.getCode());
        if (apLikesBehavior != null && apLikesBehavior.getOperation() == ApLikesBehavior.Operation.LIKE.getCode()) {
            isLike = true;
        }
        //5，通过authorId去app的id  userId(app账号信息id)
        //查看关注表，根据当前登录人的userId与作者的app账号id去查询，如果有数据，就说明已经关注，没有则说明没有关注
        ApAuthor apAuthor = apAuthorMapper.selectById(dto.getAuthorId());
        if (apAuthor != null && user != null && apAuthor.getUserId() != null) {
            ApUserFollow apUserFollow = apUserFollowMapper.selectByFollowId(BurstUtils.groudOne(user.getId()), user.getId(), apAuthor.getUserId().intValue());
            if (apUserFollow != null) {
                isFollow = true;
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("isfollow", isFollow);
        data.put("islike", isLike);
        data.put("isunlike", isUnLike);
        data.put("iscollection", isCollection);

        return ResponseResult.okResult(data);
    }
}
