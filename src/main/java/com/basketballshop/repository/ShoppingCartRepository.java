package com.basketballshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.basketballshop.entity.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long>{

}
