package com.jaziel.article.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.jaziel.model.mappers.app.ApUserArticleListMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.article.service.AppArticleService;
import com.jaziel.common.article.constans.ArticleConstans;
import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.article.pojos.ApArticle;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.mappers.app.ApArticleMapper;
import com.jaziel.model.user.pojos.ApUserArticleList;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/5/18 19:38
 */
@Service
@SuppressWarnings("all")
public class AppArticleServiceImpl implements AppArticleService {

    // 设置最大连接数
    private static final short LOAD_MAX_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ApUserArticleListMapper apUserArticleListMapper;


    /**
    * @param type 1 加载更多 2 加载更新
    * @return 数据列表
    */
    @Override
    public ResponseResult load(Short type, ArticleHomeDto dto) {
        //参数校验
        if(dto ==null ){
            dto = new ArticleHomeDto();
        }
        //时间校验
        if(dto.getMaxBehotTime()==null){
            dto.setMaxBehotTime(new Date());
        }

        if(dto.getMinBehotTime()==null){
            dto.setMinBehotTime(new Date());
        }
        // 分页参数校验
        ApUser user = AppThreadLocalUtils.getUser();
        Integer size = dto.getSize();
        String tag = dto.getTag();

        if (size == null || size <= 0) {
            size = 20;
        }
        size = Math.min(size, LOAD_MAX_SIZE);
        dto.setSize(size);
        // 类型参数校验
        if (!type.equals(ArticleConstans.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstans.LOADTYPE_LOAD_NEW)) {
            type = ArticleConstans.LOADTYPE_LOAD_MORE;
        }
        // 文章频道参数验证
        if (StringUtils.isEmpty(tag)) {
            dto.setTag(ArticleConstans.DEFAULT_TAG);
        }
        // 最小时间处理
        if (dto.getMinBehotTime() == null) {
            dto.setMinBehotTime(new Date());
        }
        // 数据加载
        if (user == null) {
            return ResponseResult.okResult(getUserArticle(type, dto));
        } else {
            return ResponseResult.okResult(getUserArticle(user, type, dto));
        }
    }

    private List<ApArticle> getUserArticle(ApUser user, Short type, ArticleHomeDto dto) {
        List<ApUserArticleList> list = apUserArticleListMapper.loadArticleIdListByUser(user, type, dto);
        if (list.isEmpty()) {
            return getUserArticle(type, dto);
        }else {
            return apArticleMapper.loadArticleListByIdList(list);
        }
    }

    private List<ApArticle> getUserArticle(Short type, ArticleHomeDto dto) {
        return apArticleMapper.loadArticleListByLocation(dto, type);
    }
}
