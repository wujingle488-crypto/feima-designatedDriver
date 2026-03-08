package com.fmdj.cst.service;
import com.fmdj.cst.db.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarService {
    void insertCustomerCar(CustomerCarEntity entity);

    ArrayList<HashMap> selectCustomerCarList(long customerId);

    int deleteCustomerCarById(long id);
}
