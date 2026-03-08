package com.fmdj.mps.controller;

import com.fmdj.common.util.CommonResult;
import com.fmdj.mps.controller.form.CalculateDriveLineForm;
import com.fmdj.mps.controller.form.CalculateTripDistanceAndDurationForm;
import com.fmdj.mps.controller.form.UpdateLocationCacheForm;
import com.fmdj.mps.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/map")
@Tag(name = "MapController", description = "地图Web接口")
public class MapController {
    @Resource
    private MapService mapService;

    @PostMapping("/calculateTripDistanceAndDuration")
    @Operation(summary = "计算历程和时间接口")
    public CommonResult calculateTripDistanceAndDuration(@RequestBody @Valid CalculateTripDistanceAndDurationForm form) {
        HashMap result = mapService.calculateTripDistanceAndDuration(form.getMode(), form.getStartPlaceLatitude(), form.getStartPlaceLongitude(),
                form.getEndPlaceLatitude(), form.getEndPlaceLongitude());

        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }

    @PostMapping("/calculateBestDrivingLine")
    @Operation(summary = "计算最佳行驶路线")
    public CommonResult calculateBestDrivingLine(@RequestBody @Valid CalculateDriveLineForm form) {
        HashMap result = mapService.calculateBestDrivingLine(form.getStartPlaceLatitude(), form.getStartPlaceLongitude(),
                form.getEndPlaceLatitude(), form.getEndPlaceLongitude());

        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }
}
