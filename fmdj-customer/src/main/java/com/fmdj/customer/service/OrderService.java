package com.fmdj.customer.service;

import com.fmdj.customer.controller.form.CreateNewOrderForm;

import java.util.HashMap;

public interface OrderService {
    HashMap createOrder(CreateNewOrderForm form);
}
