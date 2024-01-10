package com.example.restcrud.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class Customers {
    private List<Customer> customers=
            new ArrayList<>();
    public Customers(){

    }
    public Customers(Spliterator<Customer> customerSpliterator) {
        customers=StreamSupport.stream(customerSpliterator,false)
                .collect(Collectors.toList());
    }
}
