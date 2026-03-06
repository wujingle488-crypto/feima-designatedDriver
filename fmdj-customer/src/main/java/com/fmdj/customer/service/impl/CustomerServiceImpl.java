package com.fmdj.customer.service.impl;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.LoginForm;
import com.fmdj.customer.controller.form.RegisterNewCustomerForm;
import com.fmdj.customer.feign.CstServiceApi;
import com.fmdj.customer.service.CustomerService;
import com.fmdj.customer.utils.ReturnMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CstServiceApi cstServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public Long registerNewCustomer(RegisterNewCustomerForm form) {
        CommonResult commonResult = cstServiceApi.registerNewCustomer(form);
        String customerId = (String) commonResult.get(CommonResult.RETURN_USER_ID);
        return Long.parseLong(customerId);
    }

    @Override
    public Long login(LoginForm form) {
        CommonResult commonResult = cstServiceApi.login(form);
        String userId = (String) commonResult.get(CommonResult.RETURN_USER_ID);

        if (!StrUtil.isBlank(userId)) {
            long customerId = Long.parseLong(userId);
            return customerId;
        }

        return null;
    }
}
