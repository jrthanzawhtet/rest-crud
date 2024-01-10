package com.example.restcrud.controller;

import com.example.restcrud.entity.Customer;
import com.example.restcrud.entity.Customers;
import com.example.restcrud.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;
    record CustomerResponse(int id,String code,
                            @JsonProperty("first_name") String firstName,
                            @JsonProperty("last_name") String lastName,
                            String address){}

    @GetMapping(value = "/customers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse findCustomerById(@PathVariable int id){
        Customer customer= customerService.findCustomerById(id);
        return toDto(customer);
    }

    private static CustomerResponse toDto(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getCode(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress()
        );
    }
    record CustomerRequest(String code,
                           @JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName,
                           String address){}
    //curl -X POST -H "Content-Type: application/json" -d '{"code":"AE","first_name":"John","last_name":"Doe","address":"Yangon"}' localhost:8080/customers
    @PostMapping(value = "/customers",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CustomerRequest request){
        return toDto(customerService.createCustomer(toEntity(request)));
    }

    @PostMapping(value="/customers/v2",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> createCustomerV2(@RequestBody CustomerRequest request)throws Exception{
        CustomerResponse response=toDto(customerService
                .createCustomer(toEntity(request)));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build()
                .created(URI.create("http://localhost:8080/api/customers/"+
                        response.id))
                .body(response);

    }
    private Customer toEntity(CustomerRequest request){
        return Customer.builder()
                .address(request.address)
                .code(request.code)
                .firstName(request.firstName)
                .lastName(request.lastName)
                .build();
    }

    @GetMapping(value = "/customers",produces = MediaType.APPLICATION_JSON_VALUE)
    public Customers listAllCustomers(){
        return customerService.listAllCustomers();
    }

}
