package com.fmdj.customer.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.*;
import com.fmdj.customer.feign.FeeCalculatorServiceApi;
import com.fmdj.customer.feign.MapServiceApi;
import com.fmdj.customer.feign.OdrServiceApi;
import com.fmdj.customer.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OdrServiceApi odrServiceApi;

    @Resource
    private MapServiceApi mapServiceApi;

    @Resource
    private FeeCalculatorServiceApi feeCalculatorServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public HashMap createOrder(CreateNewOrderForm form) {
        //从前端取出相关的参数
        Long customerId = form.getCustomerId();
        String startPlace = form.getStartPlace();
        String startPlaceLatitude = form.getStartPlaceLatitude();
        String startPlaceLongitude = form.getStartPlaceLongitude();
        String endPlace = form.getEndPlace();
        String endPlaceLatitude = form.getEndPlaceLatitude();
        String endPlaceLongitude = form.getEndPlaceLongitude();
        String favourFee = form.getFavourFee();

        /**
         * 重新调用地图服务，算出用户此时下单的预估里程和时间
         */
        CalculateTripDistanceAndDurationForm form1 = new CalculateTripDistanceAndDurationForm();
        form1.setMode("driving");
        form1.setStartPlaceLatitude(startPlaceLatitude);
        form1.setStartPlaceLongitude(startPlaceLongitude);
        form1.setEndPlaceLatitude(endPlaceLatitude);
        form1.setEndPlaceLongitude(endPlaceLongitude);
        CommonResult commonResult1 = mapServiceApi.calculateTripDistanceAndDuration(form1);
        HashMap map = (HashMap) commonResult1.get(CommonResult.RETURN_RESULT);
        //公里数
        String mileage = MapUtil.getStr(map, "mileage");
        //耗时分钟
        int minute = MapUtil.getInt(map, "minute");

        /**
         * 调用计费模块，计算代驾费用
         */
        RideChargeCalculatorForm form2 = new RideChargeCalculatorForm();

        form2.setMileage(mileage);
        form2.setTime(new DateTime().toTimeStr());
        CommonResult commonResult2 = feeCalculatorServiceApi.rideChargeCalculator(form2);

        map = (HashMap) commonResult2.get(CommonResult.RETURN_RESULT);
        String expectsFee = MapUtil.getStr(map, "amount");
        String chargeRuleId = MapUtil.getStr(map, "chargeRuleId");
        short baseMileage = MapUtil.getShort(map, "baseMileage");
        String baseMileagePrice = MapUtil.getStr(map, "baseMileagePrice");
        String exceedMileagePrice = MapUtil.getStr(map, "exceedMileagePrice");
        short baseMinute = MapUtil.getShort(map, "baseMinute");
        String exceedMinutePrice = MapUtil.getStr(map, "exceedMinutePrice");
        short baseReturnMileage = MapUtil.getShort(map, "baseReturnMileage");
        String exceedReturnPrice = MapUtil.getStr(map, "exceedReturnPrice");

        /*
         *  查找符合接单条件的司机：要计算附近几公里以内的有那些司机在线，我们就得用上Redis的GEO来计算坐标距离。
         * */
        SearchBefittingDriverAboutOrderForm form3 = new SearchBefittingDriverAboutOrderForm();
        form3.setStartPlaceLongitude(startPlaceLongitude);
        form3.setStartPlaceLatitude(startPlaceLatitude);
        form3.setEndPlaceLongitude(endPlaceLongitude);
        form3.setEndPlaceLatitude(endPlaceLatitude);
        form3.setMileage(mileage);
        CommonResult commonResult3 = mapServiceApi.selectBefittingDriverAboutOrder(form3);
        ArrayList<HashMap> list = (ArrayList<HashMap>) commonResult3.get(CommonResult.RETURN_LIST);

        HashMap result = new HashMap<>();
        //存在符合接单条件的司机就创建订单
        if (list.size() > 0) {
            /*
             * 生成订单记录
             * */
            InsertOrderForm insertOrderForm = new InsertOrderForm();
            insertOrderForm.setUuid(IdUtil.simpleUUID());
            insertOrderForm.setCustomerId(customerId);
            insertOrderForm.setStartPlace(startPlace);
            insertOrderForm.setStartPlaceLatitude(startPlaceLatitude);
            insertOrderForm.setStartPlaceLongitude(startPlaceLongitude);
            insertOrderForm.setEndPlace(endPlace);
            insertOrderForm.setEndPlaceLatitude(endPlaceLatitude);
            insertOrderForm.setEndPlaceLongitude(endPlaceLongitude);
            insertOrderForm.setExpectsMileage(mileage);
            insertOrderForm.setExpectsFee(expectsFee);
            insertOrderForm.setFavourFee(favourFee);
            insertOrderForm.setDate(new DateTime().toDateStr());
            insertOrderForm.setChargeRuleId(Long.parseLong(chargeRuleId));
            insertOrderForm.setCarPlate(form.getCarPlate());
            insertOrderForm.setCarType(form.getCarType());
            insertOrderForm.setBaseMileage(baseMileage);
            insertOrderForm.setBaseMileagePrice(baseMileagePrice);
            insertOrderForm.setExceedMileagePrice(exceedMileagePrice);
            insertOrderForm.setBaseMinute(baseMinute);
            insertOrderForm.setExceedMinutePrice(exceedMinutePrice);
            insertOrderForm.setBaseReturnMileage(baseReturnMileage);
            insertOrderForm.setExceedReturnPrice(exceedReturnPrice);
            /**
             * 调用订单模块，插入订单
             */
            CommonResult commonResult4 = odrServiceApi.insertOrder(insertOrderForm);
            String orderId = MapUtil.getStr(commonResult4, CommonResult.RETURN_ORDER_ID);

            result.put("orderId", orderId);
            result.put("count", list.size());
        }

        return result;
    }
}
