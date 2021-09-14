package com.basketballshop.service;

import com.basketballshop.entity.Order;
import com.basketballshop.entity.ShoppingCart;
import com.basketballshop.entity.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, String shippingAddress, User user);
	
	Order findOne(Long id);
}
