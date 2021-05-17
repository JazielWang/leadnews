package com.jaziel.model.behavior.dtos;

import com.heima.model.article.pojos.ApArticle;
import com.jaziel.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.List;

@Data
public class ShowBehaviorDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    List<ApArticle> articleIds;

}
