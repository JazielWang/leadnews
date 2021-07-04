package com.jaziel.media.service;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmMaterialDto;
import org.springframework.web.multipart.MultipartFile;

public interface MaterialService {
    /**
     * 上传图片接口
     * @param multipartFile*
     * @return*
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 删除图片接口
     * @param dto
     * @return
     */
    ResponseResult delPicture(WmMaterialDto dto);
}