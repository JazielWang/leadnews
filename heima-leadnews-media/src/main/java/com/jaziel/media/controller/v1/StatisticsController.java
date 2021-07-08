package com.jaziel.media.controller.v1;

import com.jaziel.apis.madia.StatisticsControllerApi;
import com.jaziel.media.service.StatisticsService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.StatisticDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/7/8 19:12
 */
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController implements StatisticsControllerApi {
    @Autowired
    private StatisticsService statisticsService;

    @Override
    @RequestMapping("/news")
    public ResponseResult newsData(@RequestBody StatisticDto dto) {
        return statisticsService.findWmNewsStatistics(dto);
    }

    @Override
    @RequestMapping("/fans")
    public ResponseResult fansData(@RequestBody StatisticDto dto) {
        return statisticsService.findFansStatistics(dto);
    }
}
