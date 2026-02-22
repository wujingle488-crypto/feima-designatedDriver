package com.fmdj.vhr.db.dao;


import com.fmdj.vhr.db.pojo.VoucherCustomerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

public interface VoucherCustomerDao {
    public int insert(VoucherCustomerEntity entity);

    public String validCanUseVoucher(Map param);

    public int bindVoucher(Map param);
}




