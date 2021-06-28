package com.jaziel.article.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaziel.article.ArticleJarApplication;
import com.jaziel.model.article.dtos.UserSearchDto;
import com.jaziel.model.user.pojos.ApUserSearch;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/6/13 20:47
 */
@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ArticleSearchControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;


    @Test
    public void findHis() throws Exception {
        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);
        dto.setPageSize(20);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/load_search_history");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delHis() throws Exception {
        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);
        ApUserSearch apUserSearch = new ApUserSearch();
        apUserSearch.setId(7100);
        List<ApUserSearch> list = new ArrayList<ApUserSearch>();
        list.add(apUserSearch);
        dto.setHisList(list);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/del_history");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void hotWord() throws Exception {
        UserSearchDto dto = new UserSearchDto();
        dto.setHotDate("2019-07-24");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/load_hot_keywords");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void associateSearch() throws Exception {
        UserSearchDto dto = new UserSearchDto();
        dto.setPageSize(20);
        dto.setSearchWords("jaziel");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/associate_search");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ESSearch() throws Exception {
        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);
        dto.setSearchWords("学习");
        dto.setPageSize(20);
        dto.setPageNum(1);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/article_search");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
