package com.cognizant.truyum.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cognizant.truyum.model.MenuItem;

@Repository
@Qualifier("cartDao")
public class CartDaoSqlImpl implements CartDao {

	private Connection conn;
	
	@Autowired	
	public void setConnection(ConnectionHandler connectionHandler) {
		try {
			this.conn=connectionHandler.getConnection();
		} catch (Exception e) {
			this.conn=null;
		}
	}

	public void addCartItem(Long userId, Long menuItemId) throws ClassNotFoundException, IOException, SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO cart(ct_us_id,ct_pr_id) VALUES(?, ?)");
		preparedStatement.setLong(1, userId);
		preparedStatement.setLong(2, menuItemId);
		preparedStatement.executeUpdate();
	}

	public Set<MenuItem> getAllCartItems(Long userId)
			throws CartEmptyException, ClassNotFoundException, IOException, SQLException {
		Set<MenuItem> menuItemList=new HashSet<MenuItem>();
		PreparedStatement preparedStatement=conn
				.prepareStatement("SELECT * FROM menu_item INNER JOIN cart ON menu_item.me_id=cart.ct_pr_id WHERE ct_us_id = ?");
		preparedStatement.setLong(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			MenuItem menuItem = new MenuItem(resultSet.getLong("me_id"), resultSet.getString("me_name"), resultSet.getFloat("me_price"),
					resultSet.getBoolean("me_active"), resultSet.getDate("me_date_of_launch"), resultSet.getString("me_category"),
					resultSet.getBoolean("me_free_delivery"));
			menuItemList.add(menuItem);
		}
		if(menuItemList.size()==0) {
			throw new CartEmptyException("Your cart is empty");
		}
		return menuItemList;
	}

	public void removeCartItem(Long userId, Long menuItemId) throws ClassNotFoundException, IOException, SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM cart WHERE ct_us_id=? AND ct_pr_id=?");
		preparedStatement.setLong(1, userId);
		preparedStatement.setLong(2, menuItemId);
		preparedStatement.executeUpdate();
		
	}

}
