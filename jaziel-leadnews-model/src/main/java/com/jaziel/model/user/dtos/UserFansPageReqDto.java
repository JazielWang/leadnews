package com.jaziel.model.user.dtos;

import com.jaziel.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class UserFansPageReqDto extends PageRequestDto {
    private String fansName;
    private Short level;
}
