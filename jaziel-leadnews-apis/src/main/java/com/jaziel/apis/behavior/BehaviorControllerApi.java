package com.jaziel.apis.behavior;

import com.jaziel.model.behavior.dtos.LikesBehaviorDto;
import com.jaziel.model.behavior.dtos.ReadBehaviorDto;
import com.jaziel.model.behavior.dtos.ShowBehaviorDto;
import com.jaziel.model.behavior.dtos.UnLikesBehaviorDto;
import com.jaziel.model.common.dtos.ResponseResult;

/**
 * @author 王杰
 * @date 2021/5/26 19:40
 */
public interface BehaviorControllerApi {
    /**
     * 保存用户行为
     * @param dto
     * @return
     */
    ResponseResult saveShowBehavior(ShowBehaviorDto dto);

    /**
     * 保存用户的西湖岸行为
     * @param dto
     * @return
     */
    ResponseResult saveLikesBehavior(LikesBehaviorDto dto);

    /**
     * 保存用户的不喜欢行为
     * @param dto
     * @return
     */
    ResponseResult saveUnlikesBehavior(UnLikesBehaviorDto dto);

    /**
     * 保存用户的阅读行为
     * @param dto
     * @return
     */
    ResponseResult saveReadBehavior(ReadBehaviorDto dto);
}
