package com.jaziel.media.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.jaziel.media.service.UserLoginService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.wemedia.WmUserMapper;
import com.jaziel.model.media.pojos.WmUser;
import com.jaziel.utils.jwt.AppJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/6/28 19:56
 */
@Service
@SuppressWarnings("all")
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private WmUserMapper wmUserMapper;

    @Override
    public ResponseResult login(WmUser user) {
        if (StringUtils.isEmpty(user.getName()) && StringUtils.isEmpty(user.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户名或密码为空");
        }
        WmUser wmUser = wmUserMapper.selectByName(user.getName());
        if (wmUser != null) {
            if (wmUser.getPassword().equals(user.getPassword())) {
                Map<String,Object> map = new HashMap<>();
                wmUser.setPassword("");
                wmUser.setSalt("");
                map.put("token", AppJwtUtil.getToken(wmUser));
                map.put("user",wmUser);
                return ResponseResult.okResult(map);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR, "密码错误");
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "该用户不存在");
        }
    }
}
