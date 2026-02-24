package com.fmdj.dr.service.impl;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.fmdj.dr.db.dao.DriverSettingsDao;
import com.fmdj.dr.service.DriverSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


@Service
@Slf4j
public class DriverSettingsServiceImpl implements DriverSettingsService {
    @Resource
    private DriverSettingsDao driverSettingsDao;


    @Override
    public HashMap<String, Object> selectDriverSettings(Long driverId) {
        String settings = driverSettingsDao.selectDriverSettings(driverId);
        HashMap map = JSONUtil.parseObj(settings).toBean(HashMap.class);
        boolean listenService = MapUtil.getInt(map, "listenService") == 1 ? true : false;
        boolean autoAccept = MapUtil.getInt(map, "autoAccept") == 1 ? true : false;
        map.replace("listenService", listenService);
        map.replace("autoAccept", autoAccept);
        return map;
    }
}
