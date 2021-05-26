package com.jaziel.model.behavior.dtos;

import com.jaziel.model.article.pojos.ApArticle;
import com.jaziel.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.List;

/**
 * @author 王杰
 */
@Data
public class ShowBehaviorDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    List<ApArticle> articleIds;

}
