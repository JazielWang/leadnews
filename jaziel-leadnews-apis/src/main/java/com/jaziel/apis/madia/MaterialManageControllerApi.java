package com.jaziel.apis.madia;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmMaterialDto;
import com.jaziel.model.media.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface MaterialManageControllerApi {
    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    ResponseResult delPicture(WmMaterialDto dto);
}