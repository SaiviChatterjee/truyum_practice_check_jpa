package com.cognizant.truyum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.repository.MenuItemRepository;
import javax.transaction.Transactional;

@Service
public class MenuItemService {
	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Transactional	
	public List<MenuItem> getMenuListAdmin(){
		return (List<MenuItem>) menuItemRepository.findAll();
	}

	@Transactional
	public List<MenuItem> getMenuListCustomer() {
		return new ArrayList<MenuItem>(menuItemRepository.getMenuItemListCustomer());
	}

	public MenuItem getMenuItem(Long menuItemId) {
		return menuItemRepository.findById(menuItemId).get();
	}

	@Transactional
	public boolean modifyMenuItem(MenuItem menuItem) {
		MenuItem menuItemDb=menuItemRepository.findById(menuItem.getId()).get();
		if(menuItemDb==null) {
			return false;
		}
		menuItemRepository.save(menuItem);
		return true;
	}

}
