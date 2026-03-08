package com.fmdj.cst.db.dao;

import com.fmdj.cst.db.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarDao {
    int insert(CustomerCarEntity entity);

    ArrayList<HashMap> selectCustomerCarList(long customerId);

    int deleteCustomerCarById(long id);
}




