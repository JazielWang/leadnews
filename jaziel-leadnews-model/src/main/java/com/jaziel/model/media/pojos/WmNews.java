package com.jaziel.model.media.pojos;

import com.jaziel.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;

@Data
public class WmNews {

    private Integer id;
    @IdEncrypt
    protected Long userId;
    private String title;
    private Short type;
    @IdEncrypt
    private Integer channelId;
    private String labels;
    private Date createdTime;
    private Date submitedTime;
    private Short status;
    private Date publishTime;
    private String reason;
    @IdEncrypt
    private Integer articleId;
    private String content;
    private String images; //图片用逗号分隔
}