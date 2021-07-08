package com.jaziel.media.service;

import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmMaterialDto;
import com.jaziel.model.media.dtos.WmMaterialListDto;
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

    /**
     * 展示素材库
     * @param dto
     * @return
     */
    ResponseResult findList(WmMaterialListDto dto);

    /**
     * 收藏和取消收藏素材
     * @param dto
     * @param type
     * @return
     */
    ResponseResult changeUserMaterialStatus(WmMaterialDto dto, Short type);
}