package com.customer.order.service;

import com.customer.order.dto.CustomerDto;
import com.customer.order.entity.Customer;
import com.customer.order.entity.Orders;
import com.customer.order.exception.AppException;
import com.customer.order.exception.NotFoundException;
import com.customer.order.repository.CustomerRepository;
import com.customer.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for(Customer customer: customerList){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCity(customer.getCity());
            customerDto.setFname(customer.getFName());
            customerDto.setLname(customer.getLName());
            customerDto.setId(customer.getId());
            customerDto.setOrdersList(customer.getOrdersList());
            customerDtoList.add(customerDto);
        }
        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Customer> getCustomerById(Integer id){
        Customer c;
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            c = customer.get();
            return new ResponseEntity<>(c,HttpStatus.OK);
        }else{
            throw new NotFoundException("Customer not found for given id");
        }
    }

    @Transactional
    public ResponseEntity<String> createCustomer(CustomerDto customerDto){
        try{
            if(StringUtils.isEmpty(customerDto.getFname()) || StringUtils.isEmpty(customerDto.getLname()) || StringUtils.isEmpty(customerDto.getCity())){
                throw new AppException("Customer details cannot be empty.");
            }else{
                Customer customer = new Customer();
                customer.setCity(customerDto.getCity());
                customer.setFName(customerDto.getFname());
                customer.setLName(customerDto.getLname());
                customerRepository.save(customer);

            }
        }catch (Exception e){
            throw new AppException(e.getMessage());
        }
        return new ResponseEntity<>("Customer created successfully",HttpStatus.CREATED);
    }

    @Transactional
    public  ResponseEntity<String> updateCustomer(CustomerDto customerDto){
        try{
            Optional<Customer> c = customerRepository.findById(customerDto.getId());
            if(c.isPresent()){
                Customer customer = c.get();
                customer.setLName(customerDto.getLname());
                customer.setCity(customerDto.getCity());
                customer.setFName(customerDto.getFname());
                customerRepository.saveAndFlush(customer);

            }else{
                throw new NotFoundException("Customer not found.");
            }
        }catch (Exception e){
            throw new AppException(e.getMessage());
        }
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteCustomerById(Integer id){
        try{
            Optional<Customer> c = customerRepository.findById(id);
            if(c.isPresent()){
                if(!c.get().getOrdersList().isEmpty()){
                    for(Orders order: c.get().getOrdersList()){
                        orderRepository.deleteById(order.getOid());
                    }
                }
                customerRepository.deleteById(id);
            }else{
                throw new NotFoundException("Customer not found.");
            }
        }catch (Exception e){
            throw new AppException(e.getMessage());
        }
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }
}
