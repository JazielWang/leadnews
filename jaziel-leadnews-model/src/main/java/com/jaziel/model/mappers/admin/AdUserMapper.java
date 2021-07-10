package com.jaziel.model.mappers.admin;

import com.jaziel.model.admin.pojos.AdUser;

public interface AdUserMapper {
    AdUser selectByName(String name);
}