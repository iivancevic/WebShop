package com.basketballshop.service;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basketballshop.entity.CartItem;
import com.basketballshop.entity.Product;
import com.basketballshop.entity.ProductToCartItem;
import com.basketballshop.entity.ShoppingCart;
import com.basketballshop.entity.User;
import com.basketballshop.repository.CartItemRepository;
import com.basketballshop.repository.ProductToCartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductToCartItemRepository productToCartItemRepository;
	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
		
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	public CartItem updateCartItem(CartItem cartItem) {

		BigDecimal bigDecimal = new BigDecimal(cartItem.getProduct().getPrice()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}

	public CartItem addProductToCartItem(Product product, User user, int qty, String itemSize) {

		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem : cartItemList) {
			if ((product.getId() == cartItem.getProduct().getId()) &  (cartItem.getItemSize().equals(itemSize))) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProduct(product);
		cartItem.setQty(qty);
		cartItem.setItemSize(itemSize);
		cartItem.setSubtotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(qty)));
		
		cartItem = cartItemRepository.save(cartItem);
		
		ProductToCartItem productToCartItem = new ProductToCartItem();
		productToCartItem.setProduct(product);
		productToCartItem.setCartItem(cartItem);
		productToCartItemRepository.save(productToCartItem);
		
		return cartItem;
	}

	public CartItem findById(Long id) {
		
		return cartItemRepository.findById(id).orElse(null);
	}


	public void removeCartItem(CartItem cartItem) {
		
		productToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}

	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
