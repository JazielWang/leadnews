package com.jaziel.model.mappers.app;

import com.jaziel.model.user.pojos.ApUser;

public interface ApUserMapper {
    ApUser selectById(Integer id);
    ApUser selectByApPhone(String phone);
}