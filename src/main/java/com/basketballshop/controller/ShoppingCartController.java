package com.basketballshop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.Product;
import com.basketballshop.entity.ShoppingCart;
import com.basketballshop.entity.User;
import com.basketballshop.service.CartItemService;
import com.basketballshop.service.ProductService;
import com.basketballshop.service.ShoppingCartService;
import com.basketballshop.service.UserService;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/cart")
	public String shoppingCart(Model theModel, Principal principal) {

		User user = userService.findByUserName(principal.getName());

		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		shoppingCartService.updateShoppingCart(shoppingCart);

		theModel.addAttribute("cartItemList", cartItemList);
		theModel.addAttribute("shoppingCart", shoppingCart);

		return "shoppingCart";
	}

	@RequestMapping("/addItem")
	public String addItem(@ModelAttribute("product") Product product, @ModelAttribute("qty") String qty,
			@ModelAttribute("itemSize") String itemSize, Model theModel, Principal principal) {

		User user = userService.findByUserName(principal.getName());

		product = productService.findOne(product.getId());

		if (Integer.parseInt(qty) > product.getInStockNumber()) {

			theModel.addAttribute("notEnoughStock", true);
			return "forward:/bookDetail?id=" + product.getId();
		}

		cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty), itemSize);
		theModel.addAttribute("addProductSuccess", true);

		return "forward:/productDetail?id=" + product.getId();
	}

	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {

		cartItemService.removeCartItem(cartItemService.findById(id));

		return "forward:/shoppingCart/cart";
	}

}
