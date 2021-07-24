package com.jaziel.model.mappers.wemedia;

import com.jaziel.model.media.dtos.WmNewsPageReqDto;
import com.jaziel.model.media.pojos.WmNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/7/5 19:14
 */
public interface WmNewsMapper {
    int updateByPrimaryKeySelective(WmNews record);
    /**
     * 根据主键修改
     *
     * @param dto
     * @return
     */
    int updateByPrimaryKey(WmNews dto);

    /**
     * 添加草稿新闻
     *
     * @param dto * @return
     */

    int insertNewsForEdit(WmNews dto);

    /**
     * 查询根据dto条件
     *
     * @param dto
     * @param uid
     * @return
     */
    List<WmNews> selectBySelective(@Param("dto") WmNewsPageReqDto dto, @Param("uid") Long uid);

    /**
     * 查询总数统计
     *
     * @param dto
     * @param uid
     * @return
     */
    int countSelectBySelective(@Param("dto") WmNewsPageReqDto dto, @Param("uid") Long uid);

    /**
     * 根据文章id查询数据
     *
     * @param id
     * @return
     */
    WmNews selectNewsDetailByPrimaryKey(Integer id);

    WmNews selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);
}
