package com.basketballshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.ProductToCartItem;

@Transactional
public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long>{

	void deleteByCartItem(CartItem cartItem);
}
