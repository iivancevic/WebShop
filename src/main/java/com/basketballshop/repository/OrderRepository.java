package com.basketballshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.basketballshop.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
