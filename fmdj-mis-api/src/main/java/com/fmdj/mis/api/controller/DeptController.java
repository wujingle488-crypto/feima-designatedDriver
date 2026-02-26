package com.fmdj.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.*;
import com.fmdj.mis.api.db.pojo.DeptEntity;
import com.fmdj.mis.api.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dept")
@Tag(name = "DeptController", description = "部门Web接口")
public class DeptController {
    @Resource
    private DeptService deptService;

    @PostMapping("/selectDeptByPage")
    @Operation(summary = "查询部门分页数据")
    @SaCheckPermission(value = {"ROOT", "DEPT:SELECT"}, mode = SaMode.OR)
    public CommonResult selectDeptByPage(@RequestBody @Valid SelectDriverByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        Map param = BeanUtil.beanToMap(form);
        param.put("start", start);
        PageUtils pageUtils = deptService.selectDeptByPage(param);
        return CommonResult.ok().put(CommonResult.RETURN_PAGE, pageUtils);
    }

    @GetMapping("/selectAllDept")
    @Operation(summary = "查询所有部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:SELECT"}, mode = SaMode.OR)
    public CommonResult selectAllDept() {
        List<HashMap> list = deptService.selectAllDept();
        return CommonResult.ok().put(CommonResult.RETURN_LIST, list);
    }

    @PostMapping("/selectById")
    @Operation(summary = "根据ID查询部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:SELECT"}, mode = SaMode.OR)
    public CommonResult selectById(@RequestBody @Valid SearchDeptByIdForm form) {
        HashMap map = deptService.selectById(form.getId());
        return CommonResult.ok(map);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:INSERT"}, mode = SaMode.OR)
    public CommonResult insert(@RequestBody @Valid InsertDeptForm form) {
        DeptEntity deptEntity = BeanUtil.copyProperties(form, DeptEntity.class);
        int rows = deptService.insert(deptEntity);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/update")
    @Operation(summary = "更新部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:UPDATE"}, mode = SaMode.OR)
    public CommonResult update(@RequestBody @Valid UpdateDeptForm form) {
        DeptEntity deptEntity = BeanUtil.copyProperties(form, DeptEntity.class);
        int rows = deptService.update(deptEntity);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/deleteDeptByIds")
    @Operation(summary = "删除部门记录")
    @SaCheckPermission(value = {"ROOT", "DEPT:DELETE"}, mode = SaMode.OR)
    public CommonResult deleteDeptByIds(@RequestBody @Valid DeleteDeptByIdsForm form) {
        int rows = deptService.deleteDeptByIds(form.getIds());
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }
}
