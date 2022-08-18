package com.customer.order.controller;


import com.customer.order.dto.CustomerDto;
import com.customer.order.dto.OrderDto;
import com.customer.order.entity.Customer;
import com.customer.order.entity.Orders;
import com.customer.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

//    @GetMapping("/")
//    public ResponseEntity<List<Orders>> getAllOrders(){
//        return orderService.getAllOrders();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id){
//        return orderService.getCustomerById(id);
//    }

    @PostMapping(value = "/")
    public  ResponseEntity<String> createCustomer(@RequestBody OrderDto orderDto){
        return orderService.createOrder(orderDto);
    }
//
//    @PutMapping("/")
//    public  ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto){
//        return orderService.updateCustomer(customerDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteCustomerById(@PathVariable Integer id){
//        return orderService.deleteCustomerById(id);
//    }
}
