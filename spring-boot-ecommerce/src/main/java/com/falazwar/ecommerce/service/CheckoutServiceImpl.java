package com.falazwar.ecommerce.service;

import com.falazwar.ecommerce.dao.CustomerRepository;
import com.falazwar.ecommerce.dto.Purchase;
import com.falazwar.ecommerce.dto.PurchaseResponse;
import com.falazwar.ecommerce.entity.Customer;
import com.falazwar.ecommerce.entity.Order;
import com.falazwar.ecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

  private CustomerRepository customerRepository;

  @Autowired
  public CheckoutServiceImpl (CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  @Transactional
  public PurchaseResponse placeOrder(Purchase purchase) {

    // retrieve the order from dto
    Order order = purchase.getOrder();

    // generate tracking number
    String orderTrackingNumber = generateOrderTrackingNumber();
    order.setOrderTrackingNumber(orderTrackingNumber);

    // populate order with orderItems
    Set<OrderItem> orderItems = purchase.getOrderItems();
    orderItems.forEach(item -> order.add(item));

    // populate order with billingAddress and shippingAddress
    order.setBillingAddress(purchase.getBillingAddress());
    order.setShippingAddress(purchase.getShippingAddress());

    // populate customer with order
    Customer customer = purchase.getCustomer();

    // check if this is an existing customer
    String theEmail = customer.getEmail();

    Customer customerFromDb = customerRepository.findByEmail(theEmail);
    if (customerFromDb != null) {
      customer = customerFromDb;
    }

    customer.add(order);

    // save to the database
    customerRepository.save(customer);

    // return a response
    return new PurchaseResponse(orderTrackingNumber);

  }

  private String generateOrderTrackingNumber() {

    // generate a random UUID number (UUID version-4)
    // For details see : https://en.wikipedia.org/wiki/Universally_unique_identifier
    //

    return UUID.randomUUID().toString();
  }
}
