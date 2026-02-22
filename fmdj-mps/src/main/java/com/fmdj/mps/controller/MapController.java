package com.fmdj.mps.controller;
import com.fmdj.common.util.CommonResult;
import com.fmdj.mps.controller.form.CalculateDriveLineForm;
import com.fmdj.mps.controller.form.EstimateOrderMileageAndMinuteForm;
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

}
