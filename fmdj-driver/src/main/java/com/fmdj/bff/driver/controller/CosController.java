package com.fmdj.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.fmdj.bff.driver.controller.form.DeleteCosFileForm;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.CosUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/cos")
@Slf4j
@Tag(name = "CosController", description = "对象存储Web接口")
public class CosController {
    @Resource
    private CosUtil cosUtil;

    @PostMapping("/uploadCosPrivateFile")
    @Operation(summary = "上传文件")
    @SaCheckLogin
    public CommonResult uploadCosPrivateFile(@Param("file") MultipartFile file, @Param("module") String module) {
        if (file.isEmpty()) {
            throw new GlobalException("上传的文件不能为空");
        }

        try {
            String path = null;
            if (module.equals("driverAuth")) {
                path = "/drivers/";
            } else {
                throw new GlobalException("module格式错误");
            }
            HashMap map = cosUtil.uploadPrivateFile(file, path);
            return CommonResult.ok(map);
        } catch (Exception e) {
            log.error("文件上传错误",e);
            throw new GlobalException("文件上传错误");
        }
    }

    @PostMapping("/deleteCosPrivateFile")
    @Operation(summary = "删除文件")
    @SaCheckLogin
    public CommonResult deleteCosPrivateFile(@RequestBody @Valid DeleteCosFileForm form) {
        cosUtil.deletePrivateFile(form.getPathes());
        return CommonResult.ok();
    }
}
