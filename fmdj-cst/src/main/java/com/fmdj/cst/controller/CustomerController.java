package com.fmdj.cst.controller;
import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.cst.controller.form.LoginForm;
import com.fmdj.cst.service.CustomerService;
import com.fmdj.cst.controller.form.RegisterNewCustomerForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fmdj.cst.utils.ReturnMessage;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@Tag(name = "CustomerController",description = "客户端web接口")
public class CustomerController {

}
