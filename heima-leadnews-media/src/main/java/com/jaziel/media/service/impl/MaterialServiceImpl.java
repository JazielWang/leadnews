package com.jaziel.media.service.impl;

import com.jaziel.common.common.minio.MinioClientUtils;
import com.jaziel.media.service.MaterialService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.wemedia.WmMaterialMapper;
import com.jaziel.model.mappers.wemedia.WmNewsMaterialMapper;
import com.jaziel.model.media.dtos.WmMaterialDto;
import com.jaziel.model.media.pojos.WmMaterial;
import com.jaziel.model.media.pojos.WmUser;
import com.jaziel.utils.threadlocal.WmThreadLocalUtils;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author 王杰
 * @date 2021/7/4 11:48
 */
@Service
@SuppressWarnings("all")
public class MaterialServiceImpl implements MaterialService {
    private String buckName = "iletter";
    private String fileServerUrl = "http://localhost:9000/";

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    @Autowired
    private MinioClientUtils minioClientUtils;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        WmUser user = WmThreadLocalUtils.getUser();
        if (multipartFile == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String originFileName = multipartFile.getOriginalFilename();
        String extName = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        if (!extName.matches("(gif|png|jpg|jpeg)")) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_IMAGE_FORMAT_ERROR);
        }
        InputStream inputStream = null;
        Boolean iletter = false;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            iletter = minioClientUtils.putObject(buckName, originFileName, inputStream, "image/(gif|png|jpg|jpeg)");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        }
        WmMaterial wmMaterial = new WmMaterial();
        if (iletter) {
            //上传成功保存媒体资源到数据库
            wmMaterial.setCreatedTime(new Date());
            wmMaterial.setType((short) 0);
            wmMaterial.setUrl("minio" + "/" + buckName + "/" + originFileName);
            wmMaterial.setUserId(user.getId());
            wmMaterial.setIsCollection((short) 0);
            wmMaterialMapper.insert(wmMaterial);
            //设置返回值
            wmMaterial.setUrl(fileServerUrl + wmMaterial.getUrl());
        }
        return ResponseResult.okResult(wmMaterial);
    }

    @Override
    public ResponseResult delPicture(WmMaterialDto dto) {
        WmUser user = WmThreadLocalUtils.getUser();
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmMaterial wmMaterial = wmMaterialMapper.selectByPrimaryKey(dto.getId());
        if (wmMaterial == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        int i = wmNewsMaterialMapper.countByMid(dto.getId());
        if (i > 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,
                    "当前图片被引用");
        }
        String fileId = wmMaterial.getUrl().replace("minio" + "/" + buckName + "/", "");
        boolean flag = false;
        try {
            flag = minioClientUtils.removeObject(buckName, fileId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        }
        if (flag){
            //删除数据库记录
            wmMaterialMapper.deleteByPrimaryKey(dto.getId());
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "数据库删除失败");
        }
    }
}
