package com.jaziel.admin.controller.v1;

import com.jaziel.admin.service.CommonService;
import com.jaziel.model.admin.dtos.CommonDto;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Jaziel
 * @date 2021/7/10 20:56
 */
@RestController
@RequestMapping("/api/v1/admin/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @PostMapping("/list")
    public ResponseResult list(@RequestBody CommonDto dto) {
        return commonService.list(dto);
    }
    @PostMapping("/update")
    public ResponseResult update(@RequestBody CommonDto dto) {
        return commonService.update(dto);
    }
    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody CommonDto dto) {
        return commonService.delete(dto);
    }
}
