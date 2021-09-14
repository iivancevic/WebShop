package com.basketballshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.ShoppingCart;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long>{

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
