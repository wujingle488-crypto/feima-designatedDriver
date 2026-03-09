package com.fmdj.mps.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.mps.controller.form.RemoveLocationCacheForm;
import com.fmdj.mps.controller.form.SearchBefittingDriverAboutOrderForm;
import com.fmdj.mps.controller.form.UpdateLocationCacheForm;
import com.fmdj.mps.service.DriverPositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/driver/position")
@Tag(name = "DriverPositionController", description = "司机定位服务Web接口")
@Slf4j
public class DriverPositionController {
    @Resource
    private DriverPositionService driverPositionService;

    @PostMapping("/updatePositionCache")
    @Operation(summary = "更新司机GPS定位缓存")
    public CommonResult updatePositionCache(@RequestBody @Valid UpdateLocationCacheForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        driverPositionService.updatePositionCache(param);
        return CommonResult.ok();
    }

    @PostMapping("/removePositionCache")
    @Operation(summary = "删除司机GPS定位缓存")
    public CommonResult removePositionCache(@RequestBody @Valid RemoveLocationCacheForm form) {
        driverPositionService.removePositionCache(form.getDriverId());
        return CommonResult.ok();
    }

    @PostMapping("/selectBefittingDriverAboutOrder")
    @Operation(summary = "查询符合某个订单接单的司机列表")
    public CommonResult selectBefittingDriverAboutOrder(@RequestBody @Valid SearchBefittingDriverAboutOrderForm form) {
        try {
            double startPlaceLatitude = parseDoubleSafely(form.getStartPlaceLatitude(), "startPlaceLatitude");
            double startPlaceLongitude = parseDoubleSafely(form.getStartPlaceLongitude(), "startPlaceLongitude");
            double endPlaceLatitude = parseDoubleSafely(form.getEndPlaceLatitude(), "endPlaceLatitude");
            double endPlaceLongitude = parseDoubleSafely(form.getEndPlaceLongitude(), "endPlaceLongitude");
            double mileage = parseDoubleSafely(form.getMileage(), "mileage");

            ArrayList list = driverPositionService.selectBefittingDriverAboutOrder(
                    startPlaceLatitude, startPlaceLongitude,
                    endPlaceLatitude, endPlaceLongitude, mileage);

            return CommonResult.ok().put(CommonResult.RETURN_LIST, list);
        } catch (IllegalArgumentException e) {
            log.error("参数解析错误: {}", e.getMessage());
            return CommonResult.error(400, "参数格式错误: " + e.getMessage());
        } catch (Exception e) {
            log.error("查询合适司机失败", e);
            return CommonResult.error(500, "查询合适司机失败");
        }
    }

    private double parseDoubleSafely(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + "格式错误: " + value);
        }
    }
}
