package com.jaziel.article.test;

import com.jaziel.article.ArticleJarApplication;
import com.jaziel.article.service.AppArticleService;
import com.jaziel.common.article.constans.ArticleConstans;
import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 王杰
 * @date 2021/5/18 20:57
 */
@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleTest {

    @Autowired
    private AppArticleService appArticleService;

    @Test
    public void testArticle(){
        ApUser apUser = new ApUser();
        apUser.setId(2104l);
        AppThreadLocalUtils.setUser(apUser);
        ArticleHomeDto dto = new ArticleHomeDto();
        dto.setSize(1);
        ResponseResult data = appArticleService.load(ArticleConstans.LOADTYPE_LOAD_MORE, dto);
        System.out.println(data.getData());
    }
}
