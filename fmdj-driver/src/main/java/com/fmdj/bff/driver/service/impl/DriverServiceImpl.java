package com.fmdj.bff.driver.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.bff.driver.controller.form.*;
import com.fmdj.bff.driver.feign.DrServiceApi;
import com.fmdj.bff.driver.feign.OdrServiceApi;
import com.fmdj.bff.driver.service.DriverService;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.CosUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {
    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private OdrServiceApi odrServiceApi;

    @Resource
    private CosUtil cosUtil;

    @Override
    @Transactional
    @LcnTransaction
    public long registerDriver(RegisterDriverForm form) {
        CommonResult commonResult = drServiceApi.registerDriver(form);
        Object userId = commonResult.get("userId");
        return Long.parseLong(userId.toString());
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverAuth(UpdateDriverAuthForm form) {
        CommonResult commonResult = drServiceApi.updateDriverAuth(form);
        Object rowsObj = commonResult.get(CommonResult.RETURN_ROW);
        return Integer.parseInt(rowsObj.toString());
    }

    @Override
    public HashMap<String, Object> login(LoginForm form) {
        CommonResult loginResult = drServiceApi.login(form);
        HashMap<String, Object> map = (HashMap<String, Object>) loginResult.get(CommonResult.RETURN_RESULT);
        return map;
    }

    @Override
    public HashMap<String, Object> selectDriverInfo(SelectDriverInfoForm form) {
        CommonResult commonResult = drServiceApi.selectDriverInfo(form);
        return (HashMap<String, Object>) commonResult.get(CommonResult.RETURN_RESULT);
    }

    @Override
    public HashMap selectWorkbenchData(long driverId) {
        //查询司机当天的业务数据
        SelectDriverTodayBusinessDataForm form1 = new SelectDriverTodayBusinessDataForm();
        form1.setDriverId(driverId);
        CommonResult commonResult1 = odrServiceApi.selectDriverTodayBusinessData(form1);
        HashMap result1 =  (HashMap)commonResult1.get(CommonResult.RETURN_RESULT);

        //查询司机的设置信息
        SelectDriverSettingsForm form2 = new SelectDriverSettingsForm();
        form2.setDriverId(driverId);
        CommonResult commonResult2 = drServiceApi.selectDriverSettings(form2);
        HashMap result2 =  (HashMap)commonResult2.get(CommonResult.RETURN_RESULT);

        HashMap result = new HashMap<>();
        result.put("business", result1);
        result.put("settings", result2);

        return result;
    }

    @Override
    public HashMap selectDriverInfoById(SelectDriverAuthForm form) {
        /**
         * 从子系统查询出来所有信息
         * */
        CommonResult commonResult = drServiceApi.selectDriverAuth(form);
        HashMap map = (HashMap) commonResult.get(CommonResult.RETURN_RESULT);

        /*
         * 获取身份证和驾驶证的图片地址
         *   例如：/drivers/fb65ac33d45046afba20519d9dc689e8.png
         * */
        String idcardFront = MapUtil.getStr(map, "idcardFront");
        String idcardBack = MapUtil.getStr(map, "idcardBack");
        String idcardHolding = MapUtil.getStr(map, "idcardHolding");
        String drcardFront = MapUtil.getStr(map, "drcardFront");
        String drcardBack = MapUtil.getStr(map, "drcardBack");
        String drcardHolding = MapUtil.getStr(map, "drcardHolding");

        /*
         * 上面的地址需要进行转换后才可以访问：
         *    需要根据数据库存储的地址来获取临时的URL外网访问地址。
         *    例如： /drivers/d3c2f6e360cd48aaad996b84beae2be4.png  =>  https://fmdj-1326451774.cos.ap-guangzhou.myqcloud.com/drivers/d3c2f6e360cd48aaad996b84beae2be4.png
         *          存储在数据库的地址                                     临时的URL外网访问地址
         * */
        String idcardFrontUrl = idcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(idcardFront) : "";
        String idcardBackUrl = idcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(idcardBack) : "";
        String idcardHoldingUrl = idcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(idcardHolding) : "";
        String drcardFrontUrl = drcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(drcardFront) : "";
        String drcardBackUrl = drcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(drcardBack) : "";
        String drcardHoldingUrl = drcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(drcardHolding) : "";

        map.put("idcardFront", idcardFrontUrl);
        map.put("idcardBack", idcardBackUrl);
        map.put("idcardHolding", idcardHoldingUrl);
        map.put("drcardFront", drcardFrontUrl);
        map.put("drcardBack", drcardBackUrl);
        map.put("drcardHolding", drcardHoldingUrl);

        return map;
    }
}
