package com.basketballshop.service;

import java.util.List;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.Product;
import com.basketballshop.entity.ShoppingCart;
import com.basketballshop.entity.User;

public interface CartItemService {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addProductToCartItem(Product product, User user, int qty, String itemSize);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
}
