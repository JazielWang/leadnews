package com.jaziel.article.service.impl;

import com.jaziel.article.service.AppArticleSearchService;
import com.jaziel.model.article.dtos.UserSearchDto;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApBehaviorEntryMapper;
import com.jaziel.model.mappers.app.ApUserSearchMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.model.user.pojos.ApUserSearch;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王杰
 * @date 2021/6/25 16:10
 */
@Service
@SuppressWarnings("all")
public class AppArticleSearchServiceImpl implements AppArticleSearchService {
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    public ResponseResult getEntryId(UserSearchDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        // 用户和设备不能同时为空
        if (user == null && dto.getEquipmentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry =
                apBehaviorEntryMapper.findByUserIdOrEquipment(userId, dto.getEquipmentId());
        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult(apBehaviorEntry.getId());
    }

    @Autowired
    private ApUserSearchMapper apUserSearchMapper;

    @Override
    public ResponseResult findUserSearch(UserSearchDto dto) {
        int pageSize = dto.getPageSize();
        if (pageSize > 50 || pageSize < 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "pageSize参数异常");
        }
        // 获得entryId
        ResponseResult entryId = getEntryId(dto);
        // 判断ResponseResult，似不似一个success的消息
        if (entryId.getCode() != AppHttpCodeEnum.SUCCESS.getCode()) {
            return entryId;
        }
        // 查询历史记录
        List<ApUserSearch> apUserSearches = apUserSearchMapper.selectByEntryId((int)entryId.getData(), dto.getPageSize());
        return ResponseResult.okResult(apUserSearches);
    }

    @Override
    public ResponseResult delUserSearch(UserSearchDto dto) {
        List<ApUserSearch> hisList = dto.getHisList();
        if (hisList == null && hisList.size() <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "pageSize参数异常");
        }
        // 获得行为实体
        ResponseResult entryId = getEntryId(dto);
        // 判断ResponseResult，似不似一个success的消息
        if (entryId.getCode() != AppHttpCodeEnum.SUCCESS.getCode()) {
            return entryId;
        }
        List<Integer> collect = dto.getHisList().stream().map(ApUserSearch::getId).collect(Collectors.toList());
        int count = apUserSearchMapper.delUserSearch((int)entryId.getData(), collect);
        return ResponseResult.okResult(count);
    }

    @Override
    public ResponseResult clearUserSearch(UserSearchDto dto) {
        ResponseResult ret = getEntryId(dto);
        if(ret.getCode()!=AppHttpCodeEnum.SUCCESS.getCode()){
            return ret;
        }
        int rows = apUserSearchMapper.clearUserSearch((Integer) ret.getData());
        return ResponseResult.okResult(rows);
    }
}
