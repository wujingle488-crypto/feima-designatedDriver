package com.fmdj.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.LoginForm;
import com.fmdj.mis.api.controller.form.SelectDriverByPageForm;
import com.fmdj.mis.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
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
}