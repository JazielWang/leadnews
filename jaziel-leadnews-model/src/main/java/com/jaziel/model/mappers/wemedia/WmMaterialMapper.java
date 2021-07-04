package com.jaziel.model.mappers.wemedia;

import com.jaziel.model.media.dtos.WmMaterialListDto;
import com.jaziel.model.media.pojos.WmMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WmMaterialMapper {
    int insert(WmMaterial record);
    WmMaterial selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    List<WmMaterial> findListByUidAndStatus(@Param("dto") WmMaterialListDto dto, @Param("uid") Long uid);
    int countListByUidAndStatus(@Param("dto") WmMaterialListDto dto, @Param("uid") Long uid);
}