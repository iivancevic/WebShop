package com.basketballshop.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basketballshop.entity.Order;
import com.basketballshop.entity.Product;
import com.basketballshop.entity.User;
import com.basketballshop.service.ProductService;
import com.basketballshop.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String showHome() {
		
		return "redirect:allProducts";
	}
	
	@RequestMapping("/admin")
	public String showAdmin() {
		
		return "redirect:/admin/productList";
	}
	
	@RequestMapping("/myProfile")
	public String myProfile(Model theModel, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		
		List<Order> orderList = user.getOrderList();
		
		theModel.addAttribute("user", user);
		theModel.addAttribute("orderList", orderList);
		return "myProfile";
	}
	
	
	@RequestMapping("/allProducts")
	public String allProducts(Model theModel) {
		
		List<Product> productList = productService.findAll();
		theModel.addAttribute("productList", productList);
		
		return "allProducts";
	}
	
	@RequestMapping("/productDetail")
	public String productDetail(@PathParam("id") Long id,
			Model theModel, Principal principal) {
		
		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUserName(username);
			theModel.addAttribute("user", user);
		}
		
		Product product = productService.findOne(id);
		
		theModel.addAttribute("product", product);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5);
		 
		List<String> sizeList = Arrays.asList("S", "M", "L", "XL");
		
		theModel.addAttribute("qtyList", qtyList);
		
		theModel.addAttribute("sizeList", sizeList);
		
		theModel.addAttribute("qty", 1);
		
		theModel.addAttribute("size", "S");
		
		return "productDetail";
	}
}










