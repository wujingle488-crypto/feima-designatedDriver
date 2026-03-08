package com.fmdj.cst.service.impl;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.cst.db.dao.CustomerCarDao;
import com.fmdj.cst.db.pojo.CustomerCarEntity;
import com.fmdj.cst.service.CustomerCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerCarServiceImpl implements CustomerCarService {
    @Resource
    private CustomerCarDao customerCarDao;

    @Override
    @Transactional
    @LcnTransaction
    public void insertCustomerCar(CustomerCarEntity entity) {
        customerCarDao.insert(entity);
    }

    @Override
    public ArrayList<HashMap> selectCustomerCarList(long customerId) {
        return customerCarDao.selectCustomerCarList(customerId);
    }

    @Override
    @Transactional
    @LcnTransaction
    public int deleteCustomerCarById(long id) {
        return customerCarDao.deleteCustomerCarById(id);
    }
}
