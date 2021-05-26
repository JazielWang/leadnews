package com.jaziel.model.mappers.app;


import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.data.repository.query.Param;

/**
 * @author 王杰
 */
public interface ApBehaviorEntryMapper {
    /**
     * 根据用户id或者设备id查询
     * @param userId 用户id
     * @param equipmentId 设备id
     * @return app行为存储
     */
    ApBehaviorEntry findByUserIdOrEquipment(@Param("userId") Long userId, @Param("equipmentId") Integer equipmentId);
}