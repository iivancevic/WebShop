package com.basketballshop.service;

import java.util.List;

import com.basketballshop.entity.Product;

public interface ProductService {

	Product save(Product product);
	
	List<Product> findAll();
	
	Product findOne(Long id);
	
	void removeOne(Long id);
	
	List<Product> findByCategory(String category);
}
