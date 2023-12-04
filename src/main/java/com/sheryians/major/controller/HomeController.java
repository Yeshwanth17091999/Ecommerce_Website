package com.sheryians.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping({"/", "/home"})
	public String homeMapping(Model theModel) {
		return "index";
		
	}
	
	@GetMapping({"/shop"})
	public String shopMapping(Model theModel) {
		theModel.addAttribute("categories", categoryService.getAllCat());
		theModel.addAttribute("products", productService.getAllProducts());
		return "shop";
		
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopCategoryType(Model model,@PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCat());
		model.addAttribute("products", productService.getProductsByCategoryId(id).orElse(null));
		
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model,@PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCat());
		model.addAttribute("product", productService.getProductById(id).get());
		
		return "viewProduct";
	}
}
