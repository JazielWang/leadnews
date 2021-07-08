package com.jaziel.model.mappers.wemedia;

import com.jaziel.model.media.dtos.StatisticDto;
import com.jaziel.model.media.pojos.WmFansStatistics;

import java.util.List;

public interface WmFansStatisticsMapper {
    List<WmFansStatistics> findByTimeAndUserId(String burst, Long userId,
                                               StatisticDto dto);
}