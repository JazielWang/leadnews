package com.jaziel.apis;

import com.jaziel.model.article.dtos.UserSearchDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/6/25 16:27
 */
public interface ArticleSearchControllerApi {
    ResponseResult findUserSearch(UserSearchDto dto);
    ResponseResult delUserSearch(UserSearchDto dto);
    ResponseResult cleanUserSearch(UserSearchDto dto);
}
