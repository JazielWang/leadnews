package com.jaziel.media.service;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmNewsDto;
import com.jaziel.model.media.dtos.WmNewsPageReqDto;
import org.apache.ibatis.annotations.Param;

public interface NewsService {
    /**
     * 自媒体发布文章
     * @param wmNews
     * @return
     */
    ResponseResult saveNews(@Param("wmNews") WmNewsDto wmNews, @Param("type") Short type);

    /**
     * 查询发布库中当前用户文章信息
     * @param dto
     * @return
     */
    ResponseResult listByUser(WmNewsPageReqDto dto);

    /**
     * 根据文章id查询文章
     * @return
     */
    ResponseResult findWmNewsById(WmNewsDto wmNews);

    /**
     * 根据文章id删除文章
     * @param wmNews
     * @return
     */
    ResponseResult delWmNewsById(WmNewsDto wmNews);
}