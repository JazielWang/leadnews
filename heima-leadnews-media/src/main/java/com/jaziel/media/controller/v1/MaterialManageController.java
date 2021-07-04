package com.jaziel.media.controller.v1;

import com.jaziel.apis.madia.MaterialManageControllerApi;
import com.jaziel.media.service.MaterialService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmMaterialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王杰
 * @date 2021/7/4 12:33
 */
@RestController
@RequestMapping("/api/v1/media/material")
public class MaterialManageController implements MaterialManageControllerApi {
    @Autowired
    private MaterialService materialService;

    @Override
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile file) {
        return materialService.uploadPicture(file);
    }

    @Override
    @PostMapping("/del_picture")
    public ResponseResult delPicture(@RequestBody WmMaterialDto dto) {
        return materialService.delPicture(dto);
    }
}
