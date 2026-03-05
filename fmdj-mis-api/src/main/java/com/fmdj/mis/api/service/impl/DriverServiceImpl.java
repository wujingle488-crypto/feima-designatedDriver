package com.fmdj.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.CosUtil;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.SelectDriverByPageForm;
import com.fmdj.mis.api.controller.form.SelectDriverRealAuthInfoForm;
import com.fmdj.mis.api.controller.form.UpdateDriverRealAuthForm;
import com.fmdj.mis.api.feign.DrServiceApi;
import com.fmdj.mis.api.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService {
    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private CosUtil cosUtil;

    @Override
    public PageUtils selectDriverByPage(SelectDriverByPageForm form) {
        CommonResult commonResult = drServiceApi.selectDriverByPage(form);
        HashMap map = (HashMap) commonResult.get(CommonResult.RETURN_RESULT);
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    public HashMap selectDriverAuthInfo(SelectDriverRealAuthInfoForm form) {
        HashMap map = new HashMap<>();

        /**
         * realAuth
         *  1未认证（认证失败），2已认证，3审核中
         *  后台管理员在认证审批的时候拒绝该司机的realAuth就会=1，需要司机重新提交资料才会更新为3
         * */
        if (form.getRealAuth() == 2 || form.getRealAuth() == 3) {
            CommonResult commonResult = drServiceApi.selectDriverAuthInfo(form);
            HashMap temp = (HashMap) commonResult.get(CommonResult.RETURN_RESULT);
            String idcardFront = MapUtil.getStr(temp, "idcardFront");
            String idcardBack = MapUtil.getStr(temp, "idcardBack");
            String idcardHolding = MapUtil.getStr(temp, "idcardHolding");
            String drcardFront = MapUtil.getStr(temp, "drcardFront");
            String drcardBack = MapUtil.getStr(temp, "drcardBack");
            String drcardHolding = MapUtil.getStr(temp, "drcardHolding");
            /*
             * 将上面的图片生成临时的外网访问URL
             * */
            idcardFront = idcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(idcardFront) : "";
            idcardBack = idcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(idcardBack) : "";
            idcardHolding = idcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(idcardHolding) : "";
            drcardFront = drcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(drcardFront) : "";
            drcardBack = drcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(drcardBack) : "";
            drcardHolding = drcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(drcardHolding) : "";

            temp.replace("idcardFront", idcardFront);
            temp.replace("idcardBack", idcardBack);
            temp.replace("idcardHolding", idcardHolding);
            temp.replace("drcardFront", drcardFront);
            temp.replace("drcardBack", drcardBack);
            temp.replace("drcardHolding", drcardHolding);

            map.put(CommonResult.DRIVER_AUTH_INFO, temp);
        }

        return map;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverRealAuth(UpdateDriverRealAuthForm form) {
        CommonResult commonResult = drServiceApi.updateDriverRealAuth(form);
        return MapUtil.getInt(commonResult, CommonResult.RETURN_ROW);
    }
}
