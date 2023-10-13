package com.falazwar.ecommerce.dto;

import com.falazwar.ecommerce.entity.Address;
import com.falazwar.ecommerce.entity.Customer;
import com.falazwar.ecommerce.entity.Order;
import com.falazwar.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

  private Customer customer;
  private Address shippingAddress;
  private Address billingAddress;
  private Order order;
  private Set<OrderItem> orderItems;

}
