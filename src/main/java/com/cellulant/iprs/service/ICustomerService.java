package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
}
