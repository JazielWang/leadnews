package com.jaziel.article.service;

import com.jaziel.model.article.dtos.UserSearchDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/6/25 16:07
 */
public interface AppArticleSearchService {
    /**
     * 查询用户的搜索历史
     * @param dto 用户搜索信息
     * @return 查询结果
     */
    ResponseResult findUserSearch(UserSearchDto dto);

    /**
     * 删除用户的搜索历史
     * @param dto
     * @return
     */
    ResponseResult delUserSearch(UserSearchDto dto);

    /**
     清空搜索历史
     @param userSearchDto
     @return
     */
    ResponseResult clearUserSearch(UserSearchDto userSearchDto);

    /**
     今日热词
     @return
     */
    ResponseResult hotKeywords(String date);

    /**
     联想词
     @param userSearchDto
     @return
     */
    ResponseResult searchAssociate(UserSearchDto userSearchDto);

    /**
     ES文章分页搜索
     @return
     */
    ResponseResult esArticleSearch(UserSearchDto userSearchDto);

    /**
     保存搜索记录
     @param entryId
     @param searchWords
     @return
     */
    ResponseResult saveUserSearch(Integer entryId, String searchWords);
}
