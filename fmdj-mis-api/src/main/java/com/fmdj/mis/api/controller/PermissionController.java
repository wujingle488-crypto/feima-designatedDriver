package com.fmdj.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.fmdj.common.util.CommonResult;
import com.fmdj.mis.api.service.PermissionService;
import com.fmdj.mis.api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/permission")
@Tag(name = "PermissionController", description = "权限Web接口")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @GetMapping("/selectAllPermission")
    @Operation(summary = "查询所有权限")
    @SaCheckPermission(value = {"ROOT", "ROLE:SELECT"}, mode = SaMode.OR)
    public CommonResult selectAllPermission(){
        ArrayList<HashMap> list = permissionService.searchAllPermission();
        return CommonResult.ok().put(CommonResult.RETURN_LIST, list);
    }
}
