package com.fmdj.odr.controller;
import cn.hutool.json.JSONObject;
import com.fmdj.common.util.CommonResult;
import com.fmdj.odr.controller.form.InsertOrderForm;
import com.fmdj.odr.controller.form.SelectDriverTodayBusinessDataForm;
import com.fmdj.odr.db.pojo.OrderBillEntity;
import com.fmdj.odr.db.pojo.OrderEntity;
import com.fmdj.odr.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/order")
@Tag(name = "OrderController",description = "订单模块接口")
public class OrderController {

}
