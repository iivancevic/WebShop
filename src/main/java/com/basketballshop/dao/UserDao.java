package com.basketballshop.dao;

import com.basketballshop.entity.User;

public interface UserDao {

	public User findByUserName(String userName);

	public User findByEmail(String email);

	public void save(User user);

}
