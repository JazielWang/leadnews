package com.jaziel.admin;

import com.jaziel.admin.service.ReviewMediaArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Jaziel
 * @date 2021/7/23 17:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewTest {
    @Autowired
    private ReviewMediaArticleService reviewMediaArticleService;

    @Test
    public void reviewTest() throws Exception {
        reviewMediaArticleService.autoReviewArticleByMedia(5100);
    }
}
