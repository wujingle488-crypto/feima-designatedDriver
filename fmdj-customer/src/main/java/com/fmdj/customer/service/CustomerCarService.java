package com.fmdj.customer.service;

import com.fmdj.customer.controller.form.DeleteCustomerCarByIdForm;
import com.fmdj.customer.controller.form.InsertCustomerCarForm;
import com.fmdj.customer.controller.form.SelectCustomerCarListForm;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarService {
    void insertCustomerCar(InsertCustomerCarForm form);

    ArrayList<HashMap> selectCustomerCarList(SelectCustomerCarListForm form);

    int deleteCustomerCarById(DeleteCustomerCarByIdForm form);
}
