package com.customer.order.service;

import com.customer.order.dto.OrderDto;
import com.customer.order.entity.Customer;
import com.customer.order.entity.Orders;
import com.customer.order.exception.AppException;
import com.customer.order.repository.CustomerRepository;
import com.customer.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<String> createOrder(OrderDto orderDto){
        try{
            Optional<Customer> c = customerRepository.findById(orderDto.getCid());
            if(c.isPresent()){
                Orders order = new Orders();
                order.setCustomer(c.get());
                order.setPName(orderDto.getPname());
                order.setQuantity(orderDto.getQuantity());
                order.setUnitPrice(orderDto.getUnitPrice());
                orderRepository.save(order);
            }
        }catch (AppException e){
            throw new AppException(e.getMessage());
        }
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }
}
