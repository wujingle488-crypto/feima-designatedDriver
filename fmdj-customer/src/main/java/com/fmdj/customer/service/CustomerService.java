package com.fmdj.customer.service;
import com.fmdj.customer.controller.form.LoginForm;
import com.fmdj.customer.controller.form.RegisterNewCustomerForm;

public interface CustomerService {
    Long registerNewCustomer(RegisterNewCustomerForm form);
}
