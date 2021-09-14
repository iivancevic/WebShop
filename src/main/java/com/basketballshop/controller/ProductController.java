package com.basketballshop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.basketballshop.entity.Product;
import com.basketballshop.service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct(Model theModel) {
		
		Product product = new Product();
		theModel.addAttribute("product", product);
		
		return "/admin/addProduct";
	}
	
	@RequestMapping(value = "/addProduct", method=RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product, HttpServletRequest request) {
		
		productService.save(product);
		
		MultipartFile productImage = product.getProductImage();
		
		try {
			byte[] bytes = productImage.getBytes();
			String name = product.getId()+".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:productList";
	}
	
	@RequestMapping("/productList")
	public String productList(Model theModel) {
		
		List<Product> productList = productService.findAll();
		theModel.addAttribute("productList", productList);
		
		return "/admin/productList";
	}
	
	@RequestMapping("/productInfo")
	public String productInfo(@RequestParam("id") Long id, Model theModel) {
		
		Product product = productService.findOne(id);
		
		theModel.addAttribute("product", product);
		
		return "/admin/productInfo";
	}
	
	@RequestMapping("/updateProduct")
	public String updateProduct(@RequestParam("id") Long id, Model theModel) {
		
		Product product = productService.findOne(id);
		
		theModel.addAttribute("product", product);
		
		return "/admin/updateProduct";
	}
	
	@RequestMapping(value="/updateProduct", method=RequestMethod.POST)
	public String updateBookPost(@ModelAttribute("product") Product product, HttpServletRequest request) {
		
		productService.save(product);
		
		MultipartFile productImage = product.getProductImage();
		
		if (!productImage.isEmpty()) {
			try {
				byte[] bytes = productImage.getBytes();
				String name = product.getId()+".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/product/" + name));
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/admin/productInfo?id=" + product.getId();
	}
	
	@RequestMapping("remove")
	public String remove(@ModelAttribute("id")Long id) {
		
		productService.removeOne(id);
		
		return "redirect:/admin/productList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
