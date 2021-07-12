package com.jaziel.admin.service.impl.commfilter;


import com.jaziel.model.admin.dtos.CommonDto;
import com.jaziel.model.admin.dtos.CommonWhereDto;
import com.jaziel.model.admin.pojos.AdUser;

public interface BaseCommonFilter {
    void doListAfter(AdUser user, CommonDto commonDto);

    void doUpdateAfter(AdUser user, CommonDto commonDto);

    void doInsertAfter(AdUser user, CommonDto commonDto);

    void doDeleteAfter(AdUser user, CommonDto commonDto);

    /**
     * 获取更新字段里面的值
     *
     * @param field 更新字段
     * @param dto   commondto对象
     * @return where条件
     */
    default CommonWhereDto findUpdateValue(String field, CommonDto dto) {
        for (CommonWhereDto set : dto.getSets()) {
            if (field.equals(set.getFiled())) {
                return set;
            }
        }
        return null;
    }

    /**
     * 获取查询字段里面的值
     *
     * @param field 更新字段
     * @param dto   commondto对象
     * @return where条件
     */
    default CommonWhereDto findWhereValue(String field, CommonDto dto) {
        if (dto != null) {
            for (CommonWhereDto cw : dto.getWhere()) {
                if (field.equals(cw.getFiled())) {
                    return cw;
                }
            }
        }
        return null;
    }
}
