package com.jaziel.model.mappers.app;

import com.jaziel.model.article.pojos.ApAuthor;

public interface ApAuthorMapper {
    ApAuthor selectById(Integer id);
    ApAuthor selectByAuthorName(String authorName);
    void insert(ApAuthor apAuthor);
}