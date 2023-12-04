package com.sheryians.major.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheryians.major.model.Category;
import com.sheryians.major.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	
	public void AddCat(Category theCategory) {
		categoryRepository.save(theCategory);
	}
	
	public List<Category> getAllCat(){
		return categoryRepository.findAll();
		}
	
	public void removeCatById(int id) {
		categoryRepository.deleteById(id);
	}
	
	public Category getCatById(int id) {
		return categoryRepository.getOne(id);
	}
}
