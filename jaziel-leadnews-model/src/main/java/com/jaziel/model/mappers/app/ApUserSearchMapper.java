package com.jaziel.model.mappers.app;

import com.jaziel.model.user.pojos.ApUserSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApUserSearchMapper {
    /**
     * 根据entryId查询搜索记录
     *
     * @param entryId 查询实体id
     * @param limit   限定数据条数
     * @return 查询实体
     */
    List<ApUserSearch> selectByEntryId(@Param("entryId") Integer entryId, @Param("limit") int limit);

    /**
     * 删除查询历史
     *
     * @param entryId 查询实体id
     * @param hisIds  查询历史的id
     * @return 删除的条数
     */
    int delUserSearch(@Param("entryId") Integer entryId, @Param("hisIds") List<Integer> hisIds);

    /**
     * 清空用户搜索记录
     *
     * @param entryId
     * @return
     */
    int clearUserSearch(Integer entryId);

    /**
     * 插入搜索记录
     *
     * @param record
     * @return
     */
    int insert(ApUserSearch record);

    /**
     * 查询记录是否存在
     *
     * @param entryId
     * @param keyword
     * @return
     */
    int checkExist(@Param("entryId") Integer entryId, @Param("keyword") String keyword);
}