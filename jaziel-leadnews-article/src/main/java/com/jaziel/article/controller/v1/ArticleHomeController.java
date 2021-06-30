package com.jaziel.article.controller.v1;

import com.jaziel.apis.article.ArticleHomeControllerApi;
import com.jaziel.article.service.AppArticleService;
import com.jaziel.common.article.constans.ArticleConstans;
import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/5/18 19:20
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController implements ArticleHomeControllerApi {

    @Autowired
    private AppArticleService articleHomeDtoService;

    @Override
    @GetMapping("/load")
    public ResponseResult load(ArticleHomeDto dto) {
        System.out.println("load done");
        return articleHomeDtoService.load(ArticleConstans.LOADTYPE_LOAD, dto);
    }

    @Override
    @GetMapping("/loadmore")
    public ResponseResult loadMore(ArticleHomeDto dto) {
        System.out.println("loadmore done");
        return articleHomeDtoService.load(ArticleConstans.LOADTYPE_LOAD_MORE, dto);

    }

    @Override
    @GetMapping("/loadnew")
    public ResponseResult loadNew(ArticleHomeDto dto) {
        return articleHomeDtoService.load(ArticleConstans.LOADTYPE_LOAD_NEW, dto);
    }
}
