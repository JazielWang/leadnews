package com.jaziel.apis.madia;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmNewsDto;
import com.jaziel.model.media.dtos.WmNewsPageReqDto;

public interface NewsControllerApi {
    /**
     * 提交文章*
     *
     * @param wmNews*
     * @return*
     */
    ResponseResult summitNews(WmNewsDto wmNews);

    /**
     * 保存草稿
     *
     * @param wmNews
     * @return
     */
    ResponseResult saveDraftNews(WmNewsDto wmNews);

    /**
     * 用户查询
     * @return
     */
    ResponseResult listByUser(WmNewsPageReqDto dto);

    /**
     * 根据id获取文章信息
     * @param wmNews
     * @return
     */
    ResponseResult wmNews(WmNewsDto wmNews);

    /**
     * 删除文章
     * @param wmNewsDto
     * @return
     */
    ResponseResult delNews(WmNewsDto wmNewsDto);
}