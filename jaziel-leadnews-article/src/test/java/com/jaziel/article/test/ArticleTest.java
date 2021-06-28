package com.jaziel.article.test;

import com.jaziel.article.ArticleJarApplication;
import com.jaziel.article.service.AppArticleService;
import com.jaziel.common.article.constans.ArticleConstans;
import com.jaziel.common.common.contants.ESIndexConstants;
import com.jaziel.common.common.pojo.EsIndexEntity;
import com.jaziel.model.article.dtos.ArticleHomeDto;
import com.jaziel.model.article.pojos.ApArticle;
import com.jaziel.model.article.pojos.ApArticleContent;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.mappers.app.ApArticleContentMapper;
import com.jaziel.model.mappers.app.ApArticleMapper;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.utils.common.ZipUtils;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/5/18 20:57
 */
@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings("all")
public class ArticleTest {

    @Autowired
    private AppArticleService appArticleService;
    @Autowired
    private JestClient jestClient;
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

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

    @Test
    public void ESTest() throws IOException {
        ArticleHomeDto dto = new ArticleHomeDto();
        dto.setSize(50);
        dto.setTag("__all__");
        List<ApArticle> apArticles =
                apArticleMapper.loadArticleListByLocation(dto, null);
        for (ApArticle apArticle : apArticles) {
            ApArticleContent apArticleContent =
                    apArticleContentMapper.selectByArticleId(apArticle.getId());
            EsIndexEntity esIndexEntity = new EsIndexEntity();
            esIndexEntity.setChannelId(new Long(apArticle.getChannelId()));
            esIndexEntity.setContent(ZipUtils.gunzip(apArticleContent.getContent()));
            esIndexEntity.setPublishTime(apArticle.getPublishTime());
            esIndexEntity.setStatus(new Long(1));
            esIndexEntity.setTag("article");
            esIndexEntity.setTitle(apArticle.getTitle());
            Index.Builder builder = new Index.Builder(esIndexEntity);
            builder.id(apArticle.getId().toString());
            builder.refresh(true);
            Index index =
                    builder.index(ESIndexConstants.ARTICLE_INDEX).type(ESIndexConstants.DEFAULT_DOC).
                            build();
            JestResult result = jestClient.execute(index);
            if (result != null && !result.isSucceeded()) {
                throw new RuntimeException(result.getErrorMessage() + "插入更新索引失败!");
            }
        }
    }
}
