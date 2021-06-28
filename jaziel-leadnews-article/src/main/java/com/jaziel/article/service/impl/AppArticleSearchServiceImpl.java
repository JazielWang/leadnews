package com.jaziel.article.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.jaziel.article.service.AppArticleSearchService;
import com.jaziel.common.common.contants.ESIndexConstants;
import com.jaziel.model.article.dtos.UserSearchDto;
import com.jaziel.model.article.pojos.ApArticle;
import com.jaziel.model.article.pojos.ApAssociateWords;
import com.jaziel.model.article.pojos.ApHotWords;
import com.jaziel.model.behavior.pojos.ApBehaviorEntry;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.model.mappers.app.*;
import com.jaziel.model.user.pojos.ApUser;
import com.jaziel.model.user.pojos.ApUserSearch;
import com.jaziel.utils.threadlocal.AppThreadLocalUtils;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang.time.DateFormatUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王杰
 * @date 2021/6/25 16:10
 */
@Service
@SuppressWarnings("all")
public class AppArticleSearchServiceImpl implements AppArticleSearchService {
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    public ResponseResult getEntryId(UserSearchDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        // 用户和设备不能同时为空
        if (user == null && dto.getEquipmentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry =
                apBehaviorEntryMapper.findByUserIdOrEquipment(userId, dto.getEquipmentId());
        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult(apBehaviorEntry.getId());
    }

    @Autowired
    private ApUserSearchMapper apUserSearchMapper;

    @Override
    public ResponseResult findUserSearch(UserSearchDto dto) {
        int pageSize = dto.getPageSize();
        if (pageSize > 50 || pageSize < 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "pageSize参数异常");
        }
        // 获得entryId
        ResponseResult entryId = getEntryId(dto);
        // 判断ResponseResult，似不似一个success的消息
        if (entryId.getCode() != AppHttpCodeEnum.SUCCESS.getCode()) {
            return entryId;
        }
        // 查询历史记录
        List<ApUserSearch> apUserSearches = apUserSearchMapper.selectByEntryId((int) entryId.getData(), dto.getPageSize());
        return ResponseResult.okResult(apUserSearches);
    }

    @Override
    public ResponseResult delUserSearch(UserSearchDto dto) {
        List<ApUserSearch> hisList = dto.getHisList();
        if (hisList == null && hisList.size() <= 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "pageSize参数异常");
        }
        // 获得行为实体
        ResponseResult entryId = getEntryId(dto);
        // 判断ResponseResult，似不似一个success的消息
        if (entryId.getCode() != AppHttpCodeEnum.SUCCESS.getCode()) {
            return entryId;
        }
        List<Integer> collect = dto.getHisList().stream().map(ApUserSearch::getId).collect(Collectors.toList());
        int count = apUserSearchMapper.delUserSearch((int) entryId.getData(), collect);
        return ResponseResult.okResult(count);
    }

    @Override
    public ResponseResult clearUserSearch(UserSearchDto dto) {
        ResponseResult ret = getEntryId(dto);
        if (ret.getCode() != AppHttpCodeEnum.SUCCESS.getCode()) {
            return ret;
        }
        int rows = apUserSearchMapper.clearUserSearch((Integer) ret.getData());
        return ResponseResult.okResult(rows);
    }

    @Autowired
    private ApHotWordsMapper apHotWordsMapper;

    @Override
    public ResponseResult hotKeywords(String date) {
        if (StringUtils.isEmpty(date)) {
            date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        }
        List<ApHotWords> apHotWords = apHotWordsMapper.queryByHotDate(date);
        return ResponseResult.okResult(apHotWords);
    }

    @Autowired
    private ApAssociateWordsMapper apAssociateWordsMapper;

    @Override
    public ResponseResult searchAssociate(UserSearchDto dto) {
        if (dto.getPageSize() > 50 || dto.getPageSize() < 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "pageSize参数异常");
        }
        List<ApAssociateWords> apAssociateWords = apAssociateWordsMapper.selectByAssociateWords("%" + dto.getSearchWords() + "%", dto.getPageSize());
        return ResponseResult.okResult(apAssociateWords);
    }

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Override
    public ResponseResult esArticleSearch(UserSearchDto dto) {
        //搜索词的敏感检查
        //只在第一页进行保存操作
        if (dto.getFromIndex() == 0) {
            ResponseResult result = getEntryId(dto);
            if (result.getCode() != AppHttpCodeEnum.SUCCESS.getCode()) {
                return result;
            }
            this.saveUserSearch((int) result.getData(), dto.getSearchWords());
        }
        //根据关键字查询索引库
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", dto.getSearchWords())
        );
        //设置分页
        searchSourceBuilder.from(dto.getFromIndex());
        searchSourceBuilder.size(dto.getPageSize());
        Search search = new
                Search.Builder(searchSourceBuilder.toString()).addIndex(ESIndexConstants.ARTICLE_INDEX).addType(ESIndexConstants.DEFAULT_DOC).build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<ApArticle> resultList = new ArrayList<>();
            List<SearchResult.Hit<ApArticle, Void>> hits = searchResult.getHits(ApArticle.class);

            ApArticle apArticle = new ApArticle();
            for (SearchResult.Hit<ApArticle, Void> hit : hits) {
                apArticle = apArticleMapper.selectById(Long.valueOf(hit.id));
                if (apArticle == null) {
                    continue;
                }
                resultList.add(apArticle);
            }
            return ResponseResult.okResult(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    @Override
    public ResponseResult saveUserSearch(Integer entryId, String searchWords) {
        //查询生效的记录是否存在
        int count = apUserSearchMapper.checkExist(entryId, searchWords);
        if (count > 0) {
            return ResponseResult.okResult(1);
        }
        ApUserSearch apUserSearch = new ApUserSearch();
        apUserSearch.setEntryId(entryId);
        apUserSearch.setKeyword(searchWords);
        apUserSearch.setStatus(1);
        apUserSearch.setCreatedTime(new Date());
        int row = apUserSearchMapper.insert(apUserSearch);
        return ResponseResult.okResult(row);
    }
}
