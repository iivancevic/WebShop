package com.basketballshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.basketballshop.entity.User;
import com.basketballshop.user.CrmUser;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);
	
	public User findByEmail(String email);
	
	public boolean checkRole(User user, String role);

	public void save(CrmUser crmUser);
}
