package com.fmdj.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.*;
import com.fmdj.mis.api.db.pojo.RoleEntity;
import com.fmdj.mis.api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONUtil;

@RestController
@RequestMapping("/role")
@Tag(name = "RoleController", description = "角色Web接口")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("/selectRoleByPage")
    @Operation(summary = "查询角色分页数据")
    @SaCheckPermission(value = {"ROOT", "ROLE:SELECT"}, mode = SaMode.OR)
    public CommonResult selectRoleByPage(@RequestBody @Valid SearchRoleByPageForm form) {
        Integer page = form.getPage();
        Integer length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = BeanUtil.copyProperties(form, HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = roleService.selectRoleByPage(param);
        return CommonResult.ok().put(CommonResult.RETURN_PAGE, pageUtils);
    }

    @GetMapping("/selectAllRole")
    @Operation(summary = "查询所有角色数据")
    @SaCheckPermission(value = {"ROOT", "ROLE:SELECT"}, mode = SaMode.OR)
    public CommonResult selectAllRole() {
        return CommonResult.ok().put(CommonResult.RETURN_LIST, roleService.selectAllRole());
    }

    @PostMapping("/selectById")
    @Operation(summary = "根据ID查询角色")
    @SaCheckPermission(value = {"ROOT", "ROLE:SELECT"}, mode = SaMode.OR)
    public CommonResult selectById(@RequestBody @Valid SearchRoleByIdForm form) {
        return CommonResult.ok(roleService.selectById(form.getId()));
    }

    @PostMapping("/insert")
    @Operation(summary = "添加角色")
    @SaCheckPermission(value = {"ROOT", "ROLE:INSERT"}, mode = SaMode.OR)
    public CommonResult insert(@RequestBody @Valid InsertRoleForm form) {
        RoleEntity entity = new RoleEntity();
        entity.setRoleName(form.getRoleName());
        entity.setPermissions(JSONUtil.toJsonStr(form.getPermissions()));
        entity.setDesc(form.getDesc());
        int rows = roleService.insert(entity);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色")
    @SaCheckPermission(value = {"ROOT", "ROLE:UPDATE"}, mode = SaMode.OR)
    public CommonResult update(@RequestBody @Valid UpdateRoleForm form) {
        RoleEntity roleEntity = BeanUtil.copyProperties(form, RoleEntity.class);
        int rows = roleService.update(roleEntity);
        if (rows == 1 && form.getChanged()) {
            ArrayList<Integer> list = roleService.selectUserIdByRoleId(form.getId());
            list.forEach(StpUtil::logoutByLoginId);
        }
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/deleteRoleByIds")
    @Operation(summary = "删除角色")
    @SaCheckPermission(value = {"ROOT", "ROLE:DELETE"}, mode = SaMode.OR)
    public CommonResult deleteDeptByIds(@RequestBody @Valid DeleteRoleByIdsForm form) {
        return CommonResult.ok().put(CommonResult.RETURN_ROW, roleService.deleteDeptByIds(form.getIds()));
    }
}
