package com.basketballshop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basketballshop.entity.Product;
import com.basketballshop.entity.User;
import com.basketballshop.service.ProductService;
import com.basketballshop.service.UserService;

@Controller
public class SearchController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/searchByCategory")
	public String searchByCategory(@RequestParam("category") String category, Model theModel, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		theModel.addAttribute("user", user);
		
		String classActiveCategory = "active" + category;
		classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
		classActiveCategory = classActiveCategory.replaceAll("&", "");
		theModel.addAttribute(classActiveCategory, true);
		
		List<Product> productList = productService.findByCategory(category);
		
		if (productList.isEmpty()) {
			theModel.addAttribute("emptyList", true);
			return "allProducts";
		}
		
		theModel.addAttribute("productList", productList);
		
		return "allProducts";
	}
}
