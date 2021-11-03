package com.nitish2794.demo.restwithspringboot.repository;

import com.nitish2794.demo.restwithspringboot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}