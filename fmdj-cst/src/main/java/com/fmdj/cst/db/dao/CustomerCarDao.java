package com.fmdj.cst.db.dao;

import com.fmdj.cst.db.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarDao {
    public int insert(CustomerCarEntity entity);

    public ArrayList<HashMap> selectCustomerCarList(long customerId);

    public int deleteCustomerCarById(long id);

}




