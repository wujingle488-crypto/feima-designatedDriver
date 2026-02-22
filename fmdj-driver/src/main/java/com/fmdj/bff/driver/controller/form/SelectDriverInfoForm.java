package com.fmdj.bff.driver.controller.form;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询司机个人信息表单")
public class SelectDriverInfoForm {

    @NotNull
    @Min(value = 1,message = "driverId不能小于1")
    @Schema(description = "司机id")
    private Long driverId;
}
