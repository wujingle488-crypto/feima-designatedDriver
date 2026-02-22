package com.fmdj.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询司机设置表单")
public class SelectDriverSettingsForm {

    @Schema(description = "司机编号")
    private Long driverId;
}
