package com.jaziel.media.service.impl;

import com.jaziel.common.common.contants.WmMediaConstans;
import com.jaziel.media.service.StatisticsService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.wemedia.WmFansStatisticsMapper;
import com.jaziel.model.mappers.wemedia.WmNewsStatisticsMapper;
import com.jaziel.model.mappers.wemedia.WmUserMapper;
import com.jaziel.model.media.dtos.StatisticDto;
import com.jaziel.model.media.pojos.WmFansStatistics;
import com.jaziel.model.media.pojos.WmUser;
import com.jaziel.utils.common.BurstUtils;
import com.jaziel.utils.threadlocal.WmThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/7/8 19:06
 */
@Service
@SuppressWarnings("all")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private WmNewsStatisticsMapper wmNewsStatisticsMapper;

    @Autowired
    private WmFansStatisticsMapper wmFansStatisticsMapper;

    @Autowired
    private WmUserMapper wmUserMapper;

    @Override
    public ResponseResult findWmNewsStatistics(StatisticDto dto) {
        ResponseResult responseResult = check(dto);
        if (responseResult != null) {
            return responseResult;
        }
        WmUser wmUser = queryAllUserInfo();
        String burst = BurstUtils.groudOne(wmUser.getApUserId());
        return
                ResponseResult.okResult(wmNewsStatisticsMapper.findByTimeAndUserId(burst,
                        wmUser.getApUserId(), dto));
    }

    @Override
    public ResponseResult findFansStatistics(StatisticDto dto) {
        ResponseResult check = check(dto);
        if (check != null){
            return check;
        }
        WmUser wmUser = queryAllUserInfo();
        Long userId = wmUser.getApUserId();
        String burst = BurstUtils.groudOne(userId);
        List<WmFansStatistics> byTimeAndUserId = wmFansStatisticsMapper.findByTimeAndUserId(burst, userId, dto);
        return ResponseResult.okResult(byTimeAndUserId);
    }

    private WmUser queryAllUserInfo() {
        WmUser user = WmThreadLocalUtils.getUser();
        user = wmUserMapper.selectById(user.getId());
        return user;
    }

    private ResponseResult check(StatisticDto dto) {
        if (dto == null && dto.getType() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if (WmMediaConstans.WM_NEWS_STATISTIC_CUR != dto.getType() &&
                (dto.getStime() == null || dto.getEtime() == null)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return null;
    }
}
