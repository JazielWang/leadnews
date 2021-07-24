package com.jaziel.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jaziel.admin.service.ReviewMediaArticleService;
import com.jaziel.common.aliyun.AliyunImageScanRequest;
import com.jaziel.common.aliyun.AliyunTextScanRequest;
import com.jaziel.common.common.contants.ESIndexConstants;
import com.jaziel.common.common.pojo.EsIndexEntity;
import com.jaziel.model.admin.pojos.AdChannel;
import com.jaziel.model.article.pojos.ApArticle;
import com.jaziel.model.article.pojos.ApArticleConfig;
import com.jaziel.model.article.pojos.ApArticleContent;
import com.jaziel.model.article.pojos.ApAuthor;
import com.jaziel.model.mappers.admin.AdChannelMapper;
import com.jaziel.model.mappers.app.*;
import com.jaziel.model.mappers.wemedia.WmNewsMapper;
import com.jaziel.model.mappers.wemedia.WmUserMapper;
import com.jaziel.model.media.pojos.WmNews;
import com.jaziel.model.media.pojos.WmUser;
import com.jaziel.model.user.pojos.ApUserMessage;
import com.jaziel.utils.common.Compute;
import com.jaziel.utils.common.ZipUtils;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/7/23 13:45
 */
@Service
@SuppressWarnings("all")
@Log4j2
public class ReviewMediaArticleServiceImpl implements ReviewMediaArticleService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Autowired
    private AliyunTextScanRequest aliyunTextScanRequest;

    @Autowired
    private AliyunImageScanRequest aliyunImageScanRequest;

    @Override
    public void autoReviewArticleByMedia(Integer newsId) throws Exception {
        //1.根据文章id查询文章内容，内容包括文本和图片
        WmNews wmNews = wmNewsMapper.selectByPrimaryKey(newsId);
        //人工审核 直接保存数据和创建索引
        String title = wmNews.getTitle();
        String content = wmNews.getContent();
        if (wmNews != null && wmNews.getStatus() == 4) {
            reviewSuccessSaveAll(wmNews, content, title);
            return;
        }
        //审核通过后待发布的文章，判断发布时间
        if (wmNews != null && wmNews.getStatus() == 8 && wmNews.getPublishTime().getTime() < new Date().getTime()) {
            reviewSuccessSaveAll(wmNews, content, title);
            return;
        }
        if (wmNews != null && wmNews.getStatus() == 1) {
            //2.根据文章标题匹配文章内容 匹配度
            double degree = Compute.SimilarDegree(content, title);
            if (degree <= 0) {
                updateWmNews(wmNews, (short) 2, "文章内容和标题不匹配");
                return;
            }
            // 审核文本
            List<String> images = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            JSONArray jsonArray = JSON.parseArray(content);
            handlerTextAndImages(images, sb, jsonArray);
            String response = aliyunTextScanRequest.textScanRequest(sb.toString());
            if (!"pass".equals(response)) {
                //文章内容审核没有通过
                updateWmNews(wmNews, (short) 2, "文章内容有违规行为");
                return;
            }
            // 审核图片
            String imageResponse = aliyunImageScanRequest.imageScanRequest(images);
            /*if (!"pass".equals(imageResponse)) {
                //文章内容审核没有通过
                updateWmNews(wmNews, (short) 2, "文章图片有违规行为");
                return;
            }*/
            //审核通过以后查看发布的时间，如果发布时间大于当前时间则正常发布保存时间，否则修改状态为待发布
            if (wmNews.getPublishTime() != null) {
                if (wmNews.getPublishTime().getTime() > System.currentTimeMillis()) {
                    // 定时发布
                    updateWmNews(wmNews, (short) 8, "待发布");
                } else {
                    // 立即发布
                    reviewSuccessSaveAll(wmNews, content, title);
                }
            } else {
                // 立即发布
                reviewSuccessSaveAll(wmNews, content, title);
            }
        }
    }


    private void handlerTextAndImages(List<String> images, StringBuilder sb, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;
            String type = (String) jsonObject.get("type");
            if ("image".equals(type)) {
                String value = (String) jsonObject.get("value");
                images.add(value);
            }
            if ("text".equals(type)) {
                String value = (String) jsonObject.get("value");
                sb.append(value);
            }
        }
    }


    private void updateWmNews(WmNews wmNews, short status, String message) {
        wmNews.setStatus(status);
        wmNews.setReason(message);
        wmNewsMapper.updateByPrimaryKeySelective(wmNews);
    }

    @Autowired
    private AdChannelMapper adChannelMapper;

    @Autowired
    private ApAuthorMapper apAuthorMapper;

    @Autowired
    private WmUserMapper wmUserMapper;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Value("${FILE_SERVER_URL}")
    private String fileServerUrl;

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ApUserMessageMapper apUserMessageMapper;

    private void reviewSuccessSaveAll(WmNews wmNews, String content, String title) {
        Long userId = wmNews.getUserId();
        String reason = wmNews.getReason();
        Integer channelId = wmNews.getChannelId();
        Integer articleId = wmNews.getArticleId();
        Date createdTime = wmNews.getCreatedTime();

        // ap_author
        String channelName = null;
        AdChannel adChannel = adChannelMapper.selectByPrimaryKey(channelId);
        if (adChannel != null) {
            channelName = adChannel.getName();
        }
        String authorName = null;
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if (wmUser != null) {
            authorName = wmUser.getName();
        }
        ApAuthor apAuthor = apAuthorMapper.selectByAuthorName(authorName);
        if (apAuthor == null || apAuthor.getId() == null) {
            apAuthor = new ApAuthor();
            apAuthor.setCreatedTime(createdTime);
            apAuthor.setName(authorName);
            apAuthor.setUserId(wmUser.getApUserId());
            apAuthor.setType(1);
            apAuthor.setWmUserId(wmUser.getId());
            apAuthorMapper.insert(apAuthor);
        }
        // ap_article
        ApArticle apArticle = new ApArticle();
        apArticle.setTitle(title);
        apArticle.setAuthorId(apAuthor.getId().longValue());
        apArticle.setAuthorName(authorName);
        apArticle.setCreatedTime(new Date());
        apArticle.setChannelId(channelId);
        apArticle.setChannelName(channelName);
        String images = wmNews.getImages();
        if (images != null) {
            String[] split = images.split(",");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(fileServerUrl).append(split[i]);
            }
            apArticle.setImages(sb.toString());
        }
        apArticle.setLayout(wmNews.getType());
        apArticleMapper.insert(apArticle);
        // ap_article_config, ap_article_content
        ApArticleConfig apArticleConfig = new ApArticleConfig();
        apArticleConfig.setArticleId(apArticle.getId());
        apArticleConfig.setIsComment(true);
        apArticleConfig.setIsDelete(false);
        apArticleConfig.setIsDown(false);
        apArticleConfig.setIsForward(true);
        apArticleConfigMapper.insert(apArticleConfig);

        ApArticleContent apArticleContent = new ApArticleContent();
        apArticleContent.setArticleId(apArticle.getId());
        apArticleContent.setContent(ZipUtils.gzip(wmNews.getContent()));
        apArticleContentMapper.insert(apArticleContent);

        // 创建ES索引
        EsIndexEntity esIndexEntity = new EsIndexEntity();
        esIndexEntity.setContent(content);
        esIndexEntity.setStatus(new Long(1));
        esIndexEntity.setTitle(title);
        esIndexEntity.setChannelId(Long.valueOf(channelId));
        esIndexEntity.setUserId(wmUser.getId());
        esIndexEntity.setPublishTime(new Date());
        esIndexEntity.setId(apArticle.getId().longValue());

        Index.Builder builder = new Index.Builder(esIndexEntity);
        builder.id(apArticle.getId().toString());
        builder.refresh(true);
        Index index =
                builder.index(ESIndexConstants.ARTICLE_INDEX).type(ESIndexConstants.DEFAULT_DOC).
                        build();
        JestResult result = null;
        try {
            result = jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("执行ES创建索引失败，message:{}", e.getMessage());
        }
        if (result != null && !result.isSucceeded()) {
            log.error("ES插入索引失败，message:{}", result.getErrorMessage());
        }

        //修改wmNews的状态 为  9
        wmNews.setArticleId(apArticle.getId());
        updateWmNews(wmNews, (short) 9, "审核成功");
        //通知用户审核成功
        ApUserMessage apUserMessage = new ApUserMessage();
        apUserMessage.setUserId(wmNews.getUserId());
        apUserMessage.setCreatedTime(new Date());
        apUserMessage.setIsRead(false);
        apUserMessage.setContent("文章审核成功");
        apUserMessage.setType(108);//文章审核通过
        apUserMessageMapper.insertSelective(apUserMessage);
    }
}
