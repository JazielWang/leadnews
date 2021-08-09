package com.jaziel.crawler.servicie.impl;

import com.jaziel.crawler.servicie.CrawlerNewsCommentService;
import com.jaziel.model.crawler.pojos.ClNewsComment;
import com.jaziel.model.mappers.crawerls.ClNewsCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Jaziel
 * @date 2021/8/9 14:32
 */
@Service
@SuppressWarnings("all")
public class CrawlerNewsCommentServiceImpl implements CrawlerNewsCommentService {
    @Autowired
    private ClNewsCommentMapper clNewsCommentMapper;

    @Override
    public void saveClNewsComment(ClNewsComment clNewsComment) {
        clNewsCommentMapper.insertSelective(clNewsComment);
    }
}
