package com.jaziel.article.service.impl;

import com.jaziel.article.service.AppArticleInfoService;
import com.jaziel.model.article.pojos.ApArticleConfig;
import com.jaziel.model.article.pojos.ApArticleContent;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.ApArticleConfigMapper;
import com.jaziel.model.mappers.app.ApArticleContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/6/13 20:30
 */
@Service
@SuppressWarnings("all")
public class AppArticleInfoServiceImpl implements AppArticleInfoService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Override
    public ResponseResult getArticleInfo(Integer ArticleId) {
        if (ArticleId == null || ArticleId < 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApArticleConfig apArticleConfig = apArticleConfigMapper.selectByArticleId(ArticleId);
        Map<String, Object> data = new HashMap<>();
        if (apArticleConfig == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        } else if (!apArticleConfig.getIsDelete()) {
            // 没删除的标识才返回给客户端
            ApArticleContent apArticleContent = apArticleContentMapper.selectByArticleId(ArticleId);

            data.put("content", apArticleContent);
        }
        data.put("config", apArticleConfig);
        return ResponseResult.okResult(data);
    }
}
