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
    @Resource
    private OrderService orderService;

    @PostMapping("/selectDriverTodayBusinessData")
    @Operation(summary = "查询司机当天营业数据")
    public CommonResult selectDriverTodayBusinessData(@RequestBody @Valid SelectDriverTodayBusinessDataForm form) {
        HashMap<String, Object> result = orderService.selectDriverTodayBusinessData(form.getDriverId());
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }

    @PostMapping("/insertOrder")
    @Operation(summary = "乘客下单")
    public CommonResult insertOrder(@RequestBody @Valid InsertOrderForm form) {
        OrderEntity orderEntity = new OrderEntity();
        /*
        * 保存订单记录
        * */
        orderEntity.setUuid(form.getUuid());
        orderEntity.setCustomerId(form.getCustomerId());
        orderEntity.setStartPlace(form.getStartPlace());
        JSONObject json = new JSONObject();
        json.set("latitude", form.getStartPlaceLatitude());
        json.set("longitude", form.getStartPlaceLongitude());
        orderEntity.setStartPlaceLocation(json.toString());
        orderEntity.setEndPlace(form.getEndPlace());
        json = new JSONObject();
        json.set("latitude", form.getEndPlaceLatitude());
        json.set("longitude", form.getEndPlaceLongitude());
        orderEntity.setEndPlaceLocation(json.toString());
        orderEntity.setExpectsMileage(new BigDecimal(form.getExpectsMileage()));
        orderEntity.setExpectsFee(new BigDecimal(form.getExpectsFee()));
        orderEntity.setFavourFee(new BigDecimal(form.getFavourFee()));
        orderEntity.setChargeRuleId(form.getChargeRuleId());
        orderEntity.setCarPlate(form.getCarPlate());
        orderEntity.setCarType(form.getCarType());
        orderEntity.setDate(form.getDate());

        /*
        * 保存订单费用
        * */
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setBaseMileage(form.getBaseMileage());
        orderBillEntity.setBaseMileagePrice(new BigDecimal(form.getBaseMileagePrice()));
        orderBillEntity.setExceedMileagePrice(new BigDecimal(form.getExceedMileagePrice()));
        orderBillEntity.setBaseMinute(form.getBaseMinute());
        orderBillEntity.setExceedMinutePrice(new BigDecimal(form.getExceedMinutePrice()));
        orderBillEntity.setBaseReturnMileage(form.getBaseReturnMileage());
        orderBillEntity.setExceedReturnPrice(new BigDecimal(form.getExceedReturnPrice()));
        String orderId = orderService.insert(orderEntity, orderBillEntity);
        return CommonResult.ok().put(CommonResult.RETURN_ORDER_ID, orderId);
    }
}
