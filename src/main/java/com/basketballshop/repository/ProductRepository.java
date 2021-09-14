package com.basketballshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.basketballshop.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

	List<Product> findByCategory(String category);
}
