package com.jaziel.crawler.servicie.impl;

import com.jaziel.common.common.util.HMStringUtils;
import com.jaziel.crawler.servicie.AdLabelService;
import com.jaziel.model.admin.pojos.AdChannelLabel;
import com.jaziel.model.admin.pojos.AdLabel;
import com.jaziel.model.mappers.admin.AdChannelLabelMapper;
import com.jaziel.model.mappers.admin.AdLabelMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Jaziel
 * @date 2021/8/6 14:31
 */
@Service
@SuppressWarnings("all")
@Log4j2
public class AdLabelServiceImpl implements AdLabelService {

    @Autowired
    private AdLabelMapper adLabelMapper;

    @Autowired
    private AdChannelLabelMapper adChannelLabelMapper;

    @Override
    public String getLableIds(String labels) {
        long currentTime = System.currentTimeMillis();
        log.info("获取channel信息，标签：labels：{}", labels);
        List<AdLabel> adLabelList = new ArrayList<AdLabel>();

        if (labels != null && !labels.isEmpty()) {
            //转换成小写
            labels = labels.toLowerCase();
            List<String> labelsList = Arrays.asList(labels.split(","));
            labelsList = new ArrayList<>(labelsList);
            log.info("查询label数组：{}", labelsList);
            List<AdLabel> tempLabels = adLabelMapper.queryAdLabelByLabels(labelsList);
            if (tempLabels != null && !tempLabels.isEmpty()) {
                adLabelList = addLabels(tempLabels, labelsList);
            } else {
                adLabelList = addLabels(labelsList);
            }
        }

        List<String> labelIdList = adLabelList.stream().map(label ->
                HMStringUtils.toString(label.getId())).collect(Collectors.toList());
        String labelIds = HMStringUtils.listToStr(labelIdList, ",");
        log.info("获取channel信息完成，标签：labels：{},labelIds:{},耗时：{}",
                labels, labelIds, System.currentTimeMillis() - currentTime);
        return labelIds;
    }

    private List<AdLabel> addLabels(List<String> labelList) {
        List<AdLabel> adLabelList = new ArrayList<AdLabel>();
        if (null != labelList && !labelList.isEmpty()) {
            for (String label : labelList) {
                adLabelList.add(addLabel(label));
            }
        }
        return adLabelList;
    }

    /**
     * 添加label
     *
     * @param label
     */
    public AdLabel addLabel(String label) {
        AdLabel adLabel = new AdLabel();
        adLabel.setName(label);
        adLabel.setType(true);
        adLabel.setCreatedTime(new Date());
        adLabelMapper.insertSelective(adLabel);
        return adLabel;
    }

    private List<AdLabel> addLabels(List<AdLabel> adLabelList, List<String> tmpLabels) {
        if (!tmpLabels.isEmpty()){
            for (AdLabel adLabel : adLabelList) {
                for (int i = 0; i < tmpLabels.size(); i++) {
                    if (tmpLabels.get(i).equals(adLabel.getName())){
                        tmpLabels.remove(i);
                    }
                }
            }
        }
        if (tmpLabels != null && !tmpLabels.isEmpty()) {
            adLabelList.addAll(addLabels(tmpLabels));
        }
        return adLabelList;
    }

    @Override
    public Integer getAdChannelByLabelIds(String labelIds) {
        Integer channelId = 0;
        try {
            channelId = getSecurityAdChannelByLabelIds(labelIds);
        }catch (Exception e){
            log.error("获取channel信息失败，errorMsg:{}",e.getMessage());
        }
        return channelId;
    }

    private Integer getSecurityAdChannelByLabelIds(String labelIds) {
        long currentTimeMillis = System.currentTimeMillis();
        log.info("获取channel信息，标签ids:{}",labelIds);
        Integer channelId = 0;
        if(StringUtils.isNotEmpty(labelIds)){//1,2,3
            List<String> labelList = Arrays.asList(labelIds.split(","));
            List<AdLabel> adLabelList = adLabelMapper.queryAdLabelByLabelIds(labelList);
            if(null != adLabelList && !adLabelList.isEmpty()){
                channelId = getAdChannelIdByLabelId(adLabelList.get(0).getId());
            }
            channelId = channelId == null ?0:channelId;
        }

        log.info("获取channel信息完成，标签:{},channelId:{},耗时:{}",labelIds,channelId,System.currentTimeMillis()-currentTimeMillis);
        return channelId;
    }

    private Integer getAdChannelIdByLabelId(Integer labelId) {
        Integer channelId = 0;
        AdChannelLabel adChannelLabel = adChannelLabelMapper.selectByLabelId(labelId);
        if(adChannelLabel!=null){
            channelId = adChannelLabel.getChannelId();
        }
        return channelId;
    }

}
