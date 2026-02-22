package com.fmdj.cst.db.dao;
import java.util.HashMap;
import java.util.Map;

public interface CustomerDao {
    public int registerNewCustomer(Map param);

    public long hasCustomer(Map param);

    public String selectCustomerIdByOpenId(String openId);

    public String login(String openId);
}
