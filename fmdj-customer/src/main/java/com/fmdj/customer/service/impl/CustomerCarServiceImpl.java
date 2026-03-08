package com.fmdj.customer.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.DeleteCustomerCarByIdForm;
import com.fmdj.customer.controller.form.InsertCustomerCarForm;
import com.fmdj.customer.controller.form.SelectCustomerCarListForm;
import com.fmdj.customer.feign.CstServiceApi;
import com.fmdj.customer.service.CustomerCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class CustomerCarServiceImpl implements CustomerCarService {
    @Resource
    private CstServiceApi cstServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public void insertCustomerCar(InsertCustomerCarForm form) {
        cstServiceApi.insertCustomerCar(form);
    }

    @Override
    public ArrayList<HashMap> selectCustomerCarList(SelectCustomerCarListForm form) {
        CommonResult commonResult = cstServiceApi.selectCustomerCarList(form);
        return (ArrayList<HashMap>) commonResult.get("result");
    }

    @Override
    @Transactional
    @LcnTransaction
    public int deleteCustomerCarById(DeleteCustomerCarByIdForm form) {
        CommonResult commonResult = cstServiceApi.deleteCustomerCarById(form);
        return MapUtil.getInt(commonResult, CommonResult.RETURN_ROW);
    }
}
