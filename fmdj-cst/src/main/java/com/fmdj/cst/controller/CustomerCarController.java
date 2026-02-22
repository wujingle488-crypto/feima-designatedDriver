package com.fmdj.cst.controller;
import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.cst.controller.form.DeleteCustomerCarByIdForm;
import com.fmdj.cst.controller.form.InsertCustomerCarForm;
import com.fmdj.cst.controller.form.SelectCustomerCarListForm;
import com.fmdj.cst.db.pojo.CustomerCarEntity;
import com.fmdj.cst.service.CustomerCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/customer/car")
@Tag(name = "CustomerCarController", description = "客户车辆Web接口")
public class CustomerCarController {

}
