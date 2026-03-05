package com.fmdj.mis.api.service;

import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.SelectDriverByPageForm;
import com.fmdj.mis.api.controller.form.SelectDriverRealAuthInfoForm;
import com.fmdj.mis.api.controller.form.UpdateDriverRealAuthForm;

import java.util.HashMap;
import java.util.Map;


public interface DriverService {
    PageUtils selectDriverByPage(SelectDriverByPageForm form);

    HashMap<String, Object> selectDriverAuthInfo(SelectDriverRealAuthInfoForm form);

    int updateDriverRealAuth(UpdateDriverRealAuthForm form);

}
