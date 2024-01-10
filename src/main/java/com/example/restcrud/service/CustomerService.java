package com.example.restcrud.service;

import com.example.restcrud.dao.CustomerDao;
import com.example.restcrud.entity.Customer;
import com.example.restcrud.entity.Customers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;

    public Customers listAllCustomers(){
        return new Customers(customerDao.
                findAll().spliterator());
    }
    public Customer createCustomer(Customer customer){
        return customerDao.save(customer);
    }

    public Customer findCustomerById(int id){
        return customerDao.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " not found!"));
    }
}









