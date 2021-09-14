package com.basketballshop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.Order;
import com.basketballshop.entity.ShoppingCart;
import com.basketballshop.entity.User;
import com.basketballshop.service.CartItemService;
import com.basketballshop.service.OrderService;
import com.basketballshop.service.ShoppingCartService;
import com.basketballshop.service.UserService;

@Controller
public class CheckoutController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/checkout")
	public String checkout(@RequestParam("id") Long cartId,
							Model theModel, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());
		
		if (cartItemList.size() == 0) {
			
			theModel.addAttribute("emptyCart", true);
			return "forward:/shoppingCart/cart";
		}
		
		for (CartItem cartItem : cartItemList) {

			if (cartItem.getProduct().getInStockNumber() < cartItem.getQty()) {
				theModel.addAttribute("notEnoughStock", true);
				return "forward:/shoppingCart/cart";
			}
		}
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		

		theModel.addAttribute("cartItemList", cartItemList);
		theModel.addAttribute("shoppingCart", shoppingCart);
		
		return "checkout";
	}
	
	@RequestMapping(value="/checkout", method=RequestMethod.POST)
	public String checkoutPost(@ModelAttribute("shippingAddress") String shippingAddress,
								Principal principal, Model theModel) {
		
		ShoppingCart shoppingCart = userService.findByUserName(principal.getName()).getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		theModel.addAttribute("cartItemList", cartItemList);
		theModel.addAttribute("shippingAddress", shippingAddress);
		
		User user = userService.findByUserName(principal.getName());
		
		Order order = orderService.createOrder(shoppingCart, shippingAddress, user);
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		
		
		return "orderSubmitted";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
