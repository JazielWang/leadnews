package com.jaziel.model.mappers.crawerls;

import com.jaziel.model.crawler.pojos.ClNews;

import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/9 14:33
 */
public interface ClNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClNews record);

    int insertSelective(ClNews record);

    ClNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClNews record);

    int updateStatus(ClNews record);

    int updateByPrimaryKeyWithBLOBs(ClNews record);

    int updateByPrimaryKey(ClNews record);

    /**
     * 按条件查询所有数据
     *
     * @param record
     * @return
     */
    List<ClNews> selectList(ClNews record);

    void deleteByUrl(String url);

    ClNews selectByIdAndStatus(ClNews param);
}
