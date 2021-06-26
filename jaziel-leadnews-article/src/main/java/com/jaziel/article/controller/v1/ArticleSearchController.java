package com.jaziel.article.controller.v1;

import com.jaziel.apis.ArticleSearchControllerApi;
import com.jaziel.article.service.AppArticleSearchService;
import com.jaziel.model.article.dtos.UserSearchDto;
import com.jaziel.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/6/25 16:29
 */
@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController implements ArticleSearchControllerApi {

    @Autowired
    private AppArticleSearchService appArticleSearchService;

    @Override
    @PostMapping("/load_search_history")
    public ResponseResult findUserSearch(@RequestBody UserSearchDto dto) {
        return appArticleSearchService.findUserSearch(dto);
    }

    @Override
    @PostMapping("/del_history")
    public ResponseResult delUserSearch(@RequestBody UserSearchDto dto) {
        return appArticleSearchService.delUserSearch(dto);
    }

    @Override
    @PostMapping("/clear_search")
    public ResponseResult cleanUserSearch(UserSearchDto dto) {
        return appArticleSearchService.clearUserSearch(dto);
    }
}
