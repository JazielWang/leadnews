package com.jaziel.behavior.test;


import com.jaziel.behavior.service.AppShowBehaviorService;
import com.jaziel.model.article.pojos.ApArticle;
import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/5/26 20:46
 */
@SpringBootTest()
@RunWith(SpringJUnit4ClassRunner.class)
public class BehaviorTest {

    @Autowired
    private AppShowBehaviorService appShowBehaviorService;

    @Test
    public void test1(){
        ApUser user = new ApUser();
        user.setId(1l);
        AppThreadLocalUtils.setUser(user);
        ShowBehaviorDto dto = new ShowBehaviorDto();
        List<ApArticle> list = new ArrayList<>();
        ApArticle apArticle = new ApArticle();
        apArticle.setId(200);
        list.add(apArticle);
        dto.setArticleIds(list);
        appShowBehaviorService.saveShowBehavior(dto);
    }
}
