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
    @Resource
    private DriverDao driverDao;

    /*获取微信服务器中的相关参数的工具类*/
    @Resource
    private MicroAppUtil microAppUtil;

    @Resource
    private DriverSettingsDao driverSettingsDao;

    @Resource
    private WalletDao walletDao;

    @Override
    @Transactional
    @LcnTransaction//分布式事务
    public String registerDriver(Map param) {
        //通过临时登录的凭证获取openId
        String code = MapUtil.getStr(param, "code");
        String openId = microAppUtil.getOpenId(code);

        HashMap<Object, Object> map = new HashMap<>() {{
            put("openId", openId);
        }};

        //校验要注册的司机是否已经存在
        long driverExists = driverDao.existsDriver(map);
        if (driverExists != 0) {
            throw new GlobalException("已经进行注册过了");
        }

        //可以注册
        param.put("openId", openId);
        //保存司机记录
        driverDao.registerDriver(param);
        //查询司机的主键值
        String driverId = driverDao.getDriverId(openId);

        DriverSettingsEntity entity = new DriverSettingsEntity();
        entity.setDriverId(Long.parseLong(driverId));
        JSONObject jsonObject = new JSONObject();
        /**
         * {
         *         "autoAccept":1,   //自动抢单
         *         "orientation":"",     //定向接单
         *         "orderDistance":0,  //订单里程不限制，司机不挑单，什么单都接
         *         "rangeDistance":5,  //只接受5公里以内的代驾单
         *         "listenService" : true //消息自动播报
         *     }
         */
        jsonObject.set("autoAccept",false);
        jsonObject.set("orientation","");
        jsonObject.set("orderDistance",0);
        jsonObject.set("rangeDistance",5);
        jsonObject.set("listenService",true);
        entity.setSettings(jsonObject.toString());
        //插入司机设置
        driverSettingsDao.insertDriversSettings(entity);

        /**
         * 添加司机钱包记录
         * */
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setDriverId(Long.parseLong(driverId));
        walletEntity.setBalance(BigDecimal.valueOf(0));
        //让用户支付的时候再设置
        walletEntity.setPassword(null);
        walletDao.insert(walletEntity);

        return driverId;
    }

}
