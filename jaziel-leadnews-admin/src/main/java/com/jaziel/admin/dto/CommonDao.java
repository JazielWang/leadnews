package com.jaziel.admin.dto;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 如果在mycat分库分表的情况下，可以提供多个方法支持不同分片算法的数据CRUD，这里演示较为常用的查询，既非复合分片的CRUD实现
 *
 * @Author Jaziel
 * @date 2021/7/10 17:29
 */
@Mapper
public interface CommonDao {
    /**
     * 根据表名查询数据
     *
     * @param tableName 表名
     * @param start     分页起始
     * @param size      分页大小
     * @return 数据列表
     */
    @Select("select * from ${tableName} limit ${start},${size}")
    @ResultType(HashMap.class)
    List<HashMap> list(@Param("tableName") String tableName, @Param("start") int start, @Param("size") int size);

    @Select("select count(*) from ${tableName}")
    @ResultType(Integer.class)
    int listCount(@Param("tableName") String tableName);

    @Select("select * from ${tableName} where 1=1 ${where} limit ${start},${size}")
    @ResultType(HashMap.class)
    List<HashMap> listForWhere(@Param("tableName") String tableName, @Param("where") String where, @Param("start") int start, @Param("size")
            int size);

    @Select("select count(*) from ${tableName} where 1=1 ${where}")
    @ResultType(Integer.class)
    int listCountWhere(@Param("tableName") String tableName, @Param("where") String where);

    @Update("update ${tableName} set ${sets} where 1=1 ${where}")
    @ResultType(Integer.class)
    int update(@Param("tableName") String tableName, @Param("where") String
            where, @Param("sets") String sets);

    @Insert("insert into ${tableName} (${fileds}) values (${values})")
    @ResultType(Integer.class)
    int insert(@Param("tableName") String tableName, @Param("fileds") String
            fileds, @Param("values") String values);

    @Delete("delete from ${tableName} where 1=1 ${where} limit 1")
    @ResultType(Integer.class)
    int delete(@Param("tableName") String tableName, @Param("where") String
            where);
}
