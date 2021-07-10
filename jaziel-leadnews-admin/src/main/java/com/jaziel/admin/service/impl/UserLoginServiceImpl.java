package com.jaziel.admin.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.jaziel.admin.service.UserLoginService;
import com.jaziel.model.admin.pojos.AdUser;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.admin.AdUserMapper;
import com.jaziel.utils.jwt.AppJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/7/9 16:41
 */
@Service
@SuppressWarnings("all")
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private AdUserMapper adUserMapper;

    @Override
    public ResponseResult login(AdUser user) {
        if (StringUtils.isEmpty(user.getName()) && StringUtils.isEmpty(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        AdUser adUser = adUserMapper.selectByName(user.getName());
        if (adUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
        }else {
            if (adUser.getPassword().equals(user.getPassword())){
                Map<String,Object> map = new HashMap<>();
                adUser.setPassword("");
                adUser.setSalt("");
                map.put("token", AppJwtUtil.getToken(adUser));
                map.put("user",adUser);
                return ResponseResult.okResult(map);
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
        }
    }
}
