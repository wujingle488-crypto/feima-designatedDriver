package com.fmdj.dr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询司机设置表单")
public class SelectDriverSettingsForm {

    @NotNull(message = "driverId不能为空")
    @Min(value = 1,message = "driverId不能小于1")
    @Schema(description = "司机id")
    private Long driverId;
}
