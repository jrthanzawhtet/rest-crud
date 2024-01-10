package com.example.restcrud.dao;

import com.example.restcrud.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDao extends CrudRepository<Customer,Integer> {
}
