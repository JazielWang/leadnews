package com.jaziel.login.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.google.common.collect.Maps;
import com.jaziel.login.service.ApUserLoginService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApUserMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.utils.jwt.AppJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 王杰
 * @date 2021/6/24 15:05
 */
@Service
@SuppressWarnings("all")
public class ApUserLoginServiceImpl implements ApUserLoginService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Override
    public ResponseResult loginAuth(ApUser user) {
        if (StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 查询用户
        ApUser apUser = apUserMapper.selectByApPhone(user.getPhone());
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        //密码错误
        if (!user.getPassword().equals(apUser.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        apUser.setPassword("");
        Map<String,Object> map = Maps.newHashMap();
        map.put("token", AppJwtUtil.getToken(apUser));
        map.put("user",apUser);
        return ResponseResult.okResult(map);
    }
}
