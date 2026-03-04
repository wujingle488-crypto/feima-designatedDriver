package com.fmdj.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.*;
import com.fmdj.mis.api.db.pojo.UserEntity;
import com.fmdj.mis.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户Web接口")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public CommonResult login(@RequestBody @Valid LoginForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        Integer userId = userService.login(param);
        CommonResult commonResult = CommonResult.ok().put(CommonResult.RETURN_RESULT, userId != null ? true : false);

        if (userId != null) {
            StpUtil.login(userId);
            Set<String> permissions = userService.searchUserPermissions(userId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            commonResult.put(CommonResult.RETURN_TOKEN,token).put("permissions", permissions);
        }
        return commonResult;
    }

    @PostMapping("/logout")
    @Operation(summary = "用户退出接口")
    public CommonResult logout() {
        StpUtil.logout();
        return CommonResult.ok();
    }

    @GetMapping("/loadUserInfo")
    @Operation(summary = "加载用户信息接口")
    @SaCheckLogin
    public CommonResult loadUserInfo(){
        int userId = StpUtil.getLoginIdAsInt();
        HashMap map = userService.loadUserInfo(userId);
        return CommonResult.ok(map);
    }

    @PostMapping("/selectUserByPage")
    @Operation(summary = "查询用户分页记录")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public CommonResult selectUserByPage(@RequestBody @Valid SearchUserByPageForm form) {
        Map param = BeanUtil.copyProperties(form, Map.class);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = userService.selectUserByPage(param);
        return CommonResult.ok().put(CommonResult.RETURN_PAGE, pageUtils);
    }

    @GetMapping("/selectAllUser")
    @Operation(summary = "查询所有用户")
    @SaCheckLogin
    public CommonResult selectAllUser() {
        ArrayList<HashMap> list = userService.selectAllUser();
        return CommonResult.ok().put("list", list);
    }

    @PostMapping("/selectById")
    @Operation(summary = "根据Id查询用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public CommonResult selectById(@RequestBody @Valid SearchUserByIdForm form) {
        HashMap map = userService.selectById(form.getUserId());
        return CommonResult.ok(map);
    }

    @PostMapping("/insert")
    @Operation(summary = "新增用户")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)
    public CommonResult insert(@RequestBody @Valid InsertUserForm form) {
        UserEntity user = BeanUtil.toBean(form, UserEntity.class);
        user.setStatus((byte) 1);
        user.setRole(JSONUtil.parseArray(form.getRole()).toString());
        user.setCreateTime(new DateTime());
        int rows = userService.insert(user);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/selectNameAndDept")
    @Operation(summary = "根据Id查询用户姓名和部门")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)
    public CommonResult selectNameAndDept(@RequestBody @Valid SearchNameAndDeptForm form) {
        HashMap map = userService.selectNameAndDept(form.getId());
        return CommonResult.ok(map);
    }

    @PostMapping("/update")
    @Operation(summary = "更新用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:UPDATE"}, mode = SaMode.OR)
    public CommonResult update(@RequestBody @Valid UpdateUserForm form) {
        UserEntity userEntity = BeanUtil.copyProperties(form, UserEntity.class);
        userEntity.setRole(JSONUtil.parseArray(form.getRole()).toString());
        userEntity.setId(Long.parseLong(form.getUserId().toString()));
        int rows = userService.update(userEntity);
        if (rows == 1) {
            StpUtil.logout(form.getUserId());
        }
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/updatePassword")
    @Operation(summary = "修改密码")
    @SaCheckLogin
    public CommonResult updatePassword(@RequestBody @Valid UpdatePasswordForm form) {
        int userId = StpUtil.getLoginIdAsInt();
        Map<String, Object> param = BeanUtil.beanToMap(form);
        param.put("userId", userId);
        int rows = userService.updatePassword(param);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/deleteUserByIds")
    @Operation(summary = "删除用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:DELETE"}, mode = SaMode.OR)
    public CommonResult deleteUserByIds(@RequestBody @Valid DeleteUserByIdsForm form) {
        int userId = StpUtil.getLoginIdAsInt();
        if (ArrayUtil.contains(form.getIds(),userId)) {
            return CommonResult.error("您不能删除自己的帐户");
        }

        int rows = userService.delete(form.getIds());
        if (rows == 1) {
            for (Integer id : form.getIds()) {
                StpUtil.logout(id);
            }
        }

        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }
}