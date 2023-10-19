package com.falazwar.ecommerce.dao;

import com.falazwar.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {

  // SELECT * FROM orders
  // LEFT OUTER JOIN customer
  // ON orders.customer_id=customer.id
  // WHERE customer.email=:email
  Page<Order> findByCustomerEmail(@Param("email") String email, Pageable pageable);
}
