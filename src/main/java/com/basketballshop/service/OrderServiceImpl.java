package com.basketballshop.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.Order;
import com.basketballshop.entity.Product;
import com.basketballshop.entity.ShoppingCart;
import com.basketballshop.entity.User;
import com.basketballshop.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	public Order createOrder(ShoppingCart shoppingCart, String shippingAddress, User user) {

		Order order = new Order();
		order.setShippingAddress(shippingAddress);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for (CartItem cartItem : cartItemList) {
			Product product = cartItem.getProduct();
			cartItem.setOrder(order);
			product.setInStockNumber(product.getInStockNumber() - cartItem.getQty());
		}
		
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}

	public Order findOne(Long id) {
		
		return orderRepository.findById(id).orElse(null);
	}

}
