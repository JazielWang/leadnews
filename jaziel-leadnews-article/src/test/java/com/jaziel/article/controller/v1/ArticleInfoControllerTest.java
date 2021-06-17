package com.jaziel.article.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaziel.article.ArticleJarApplication;
import com.jaziel.model.article.dtos.ArticleInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author 王杰
 * @date 2021/6/13 20:47
 */
@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ArticleInfoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    public void testMvc() throws Exception {
        ArticleInfoDto dto = new ArticleInfoDto();
        dto.setArticleId(1);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/load_article_info");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testBehavior() throws Exception {
        ArticleInfoDto dto = new ArticleInfoDto();
        dto.setArticleId(1);
        dto.setEquipmentId(1);
        dto.setAuthorId(1);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/load_article_behavior");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
