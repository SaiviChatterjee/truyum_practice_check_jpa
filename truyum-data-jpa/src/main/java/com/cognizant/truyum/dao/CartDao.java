package com.cognizant.truyum.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import com.cognizant.truyum.model.MenuItem;

public interface CartDao {
	public void addCartItem(Long userId,Long menuItemId) throws ClassNotFoundException, IOException, SQLException;
	public Set<MenuItem> getAllCartItems(Long userId) throws CartEmptyException, ClassNotFoundException, IOException, SQLException;
	public void removeCartItem(Long userId, Long menuItemId) throws ClassNotFoundException, IOException, SQLException;
}
