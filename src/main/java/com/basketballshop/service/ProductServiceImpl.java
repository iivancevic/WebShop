package com.basketballshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basketballshop.entity.Product;
import com.basketballshop.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	public Product save(Product product) {
		return productRepository.save(product);
	}

	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}

	public Product findOne(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public void removeOne(Long id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> findByCategory(String category){
		
		List<Product> productList = productRepository.findByCategory(category);
		
		List<Product> activeProductList = new ArrayList<>();
		for (Product product : productList) {
			if (product.isActive()) {
				activeProductList.add(product);
			}
		}
		
		return activeProductList;
	}

}
