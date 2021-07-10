package com.jaziel.model.admin.dtos;

import lombok.Data;

@Data
public class CommonWhereDto {

    private String field;
    private String type="eq";
    private String value;

}
