package com.fmdj.mis.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询司机分页记录表单")
public class SelectDriverByPageForm {
    private Integer page;

    private Integer length;

    private String name;

    private String tel;

    private String pid;

    private String sex;

    private Byte realAuth;

    private Byte status;
}
