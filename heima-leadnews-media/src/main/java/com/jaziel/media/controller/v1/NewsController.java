package com.jaziel.media.controller.v1;

import com.jaziel.apis.madia.NewsControllerApi;
import com.jaziel.common.common.contants.WmMediaConstans;
import com.jaziel.media.service.NewsService;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.media.dtos.WmNewsDto;
import com.jaziel.model.media.dtos.WmNewsPageReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/7/8 15:41
 */
@RestController
@RequestMapping("/api/v1/media/news")
public class NewsController implements NewsControllerApi {

    @Autowired
    private NewsService newsService;

    @Override
    @PostMapping("/submit")
    public ResponseResult summitNews(@RequestBody WmNewsDto wmNews) {
        return newsService.saveNews(wmNews, WmMediaConstans.WM_NEWS_SUMMIT_STATUS);
    }

    @Override
    @PostMapping("/save_draft")
    public ResponseResult saveDraftNews(@RequestBody WmNewsDto wmNews) {
        return newsService.saveNews(wmNews, WmMediaConstans.WM_NEWS_DRAFT_STATUS);
    }

    @Override
    @PostMapping("/list")
    public ResponseResult listByUser(@RequestBody WmNewsPageReqDto dto) {
        return newsService.listByUser(dto);
    }

    @Override
    @PostMapping("/news")
    public ResponseResult wmNews(@RequestBody WmNewsDto wmNews) {
        return newsService.findWmNewsById(wmNews);
    }

    @Override
    @PostMapping("/del_news")
    public ResponseResult delNews(@RequestBody WmNewsDto wmNewsDto) {
        return newsService.delWmNewsById(wmNewsDto);
    }
}
