package com.fmdj.dr.service.impl;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.common.util.MicroAppUtil;
import com.fmdj.common.util.PageUtils;
import com.fmdj.dr.db.dao.DriverDao;
import com.fmdj.dr.db.dao.DriverSettingsDao;
import com.fmdj.dr.db.dao.WalletDao;
import com.fmdj.dr.db.pojo.DriverSettingsEntity;
import com.fmdj.dr.db.pojo.WalletEntity;
import com.fmdj.dr.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

}
