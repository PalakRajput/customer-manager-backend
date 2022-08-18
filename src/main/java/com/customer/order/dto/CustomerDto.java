package com.customer.order.dto;

import com.customer.order.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer id;
    private String fname;
    private String lname;
    private String city;
    private List<Orders> ordersList;
}
