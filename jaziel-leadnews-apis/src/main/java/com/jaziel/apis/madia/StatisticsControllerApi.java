package com.jaziel.apis.madia;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.StatisticDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface StatisticsControllerApi {
    /**
     * 文章文章
     * @param dto
     * @return
     */
    public ResponseResult newsData(StatisticDto dto);

    /**
     * 粉丝数据*
     * @param dto*
     * @return*
     */
    public ResponseResult fansData(@RequestBody StatisticDto dto);
}