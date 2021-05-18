package com.jaziel.model.mess.admin;


import com.jaziel.model.article.pojos.ApArticleConfig;
import com.jaziel.model.article.pojos.ApArticleContent;
import com.jaziel.model.article.pojos.ApAuthor;
import lombok.Data;

@Data
public class AutoReviewClNewsSuccess {
    private ApArticleConfig apArticleConfig;
    private ApArticleContent apArticleContent;
    private ApAuthor apAuthor;

}
