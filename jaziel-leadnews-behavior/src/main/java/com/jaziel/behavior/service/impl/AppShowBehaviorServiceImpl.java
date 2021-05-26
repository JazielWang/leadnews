package com.jaziel.behavior.service.impl;

import com.jaziel.behavior.service.AppShowBehaviorService;
import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.behavior.pojos.ApShowBehavior;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApBehaviorEntryMapper;
import com.jaziel.model.mappers.app.ApShowBehaviorMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/5/26 19:49
 */
@Service
@SuppressWarnings("all")
public class AppShowBehaviorServiceImpl implements AppShowBehaviorService {

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApShowBehaviorMapper apShowBehaviorMapper;

    @Override
    public ResponseResult saveShowBehavior(ShowBehaviorDto dto) {
        //获取用户信息，获取设备id
        ApUser user = AppThreadLocalUtils.getUser();
        // 校验参数:用户和设备不能同时为空
        if (user == null && (dto.getArticleIds() == null || dto.getArticleIds().isEmpty())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        //根据当前的用户信息或设备id查询行为实体 ap_behavior_entry
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.findByUserIdOrEquipment(userId, dto.getEquipmentId());
        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取前台传递过来的文章列表id
        Integer[] articleIds = new Integer[dto.getArticleIds().size()];
        for (int i = 0; i < articleIds.length; i++) {
            articleIds[i] = dto.getArticleIds().get(i).getId();
        }
        //根据行为实体id和文章列表id查询app行为表  ap_show_behavior
        List<ApShowBehavior> apShowBehaviorList = apShowBehaviorMapper.findListByEntryIdAndArticleIds(apBehaviorEntry.getId(), articleIds);
        //数据的过滤，需要删除表中已经存在的文章id
        List<Integer> integers = Arrays.asList(articleIds);
        if (!apShowBehaviorList.isEmpty()) {
            apShowBehaviorList.forEach(item -> {
                Integer articleId = item.getArticleId();
                integers.remove(articleId);
            });
        }
        //保存操作
        if(!integers.isEmpty()){
            articleIds=new Integer[integers.size()];
            integers.toArray(articleIds);
            apShowBehaviorMapper.saveShowBehavior(articleIds,apBehaviorEntry.getId());
        }
        return ResponseResult.okResult(0);
    }
}
