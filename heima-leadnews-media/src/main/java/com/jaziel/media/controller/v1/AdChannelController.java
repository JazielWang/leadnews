package com.jaziel.media.controller.v1;

import com.jaziel.apis.admin.AdChannelControllerApi;
import com.jaziel.media.service.AdChannelService;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/7/8 15:46
 */
@RestController
@RequestMapping("/api/v1/channel")
public class AdChannelController implements AdChannelControllerApi {

    @Autowired
    private AdChannelService adChannelService;

    @Override
    @RequestMapping("/channels")
    public ResponseResult selectAll() {
        return ResponseResult.okResult(adChannelService.selectAll());
    }
}
