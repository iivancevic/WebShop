package com.basketballshop.dao;

import com.basketballshop.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);

}
