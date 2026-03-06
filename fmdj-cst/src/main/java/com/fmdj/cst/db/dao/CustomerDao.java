package com.fmdj.cst.db.dao;

import java.util.Map;

public interface CustomerDao {
    int registerNewCustomer(Map param);

    long hasCustomer(Map param);

    String selectCustomerIdByOpenId(String openId);

    String login(String openId);
}
