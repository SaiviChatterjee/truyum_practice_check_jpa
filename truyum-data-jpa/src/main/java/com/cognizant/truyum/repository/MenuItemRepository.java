package com.cognizant.truyum.repository;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.truyum.model.MenuItem;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItem, Long>{
	
	@Query("SELECT m FROM MenuItem m WHERE m.active=true AND m.dateOfLaunch< current_date")
	public Set<MenuItem> getMenuItemListCustomer();
}
