package com.sheryians.major.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sheryians.major.dto.ProductDTO;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;

	@GetMapping("/admin")
	public String addHome() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
			public String getCat(Model theModel) {
		theModel.addAttribute("categories",categoryService.getAllCat());
				return "categories";
			}
	
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model theModel) {
		theModel.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.AddCat(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCatById(@PathVariable int id) {
		categoryService.removeCatById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String UpdateCatById(@PathVariable int id, Model theModel) {
		theModel.addAttribute("category", categoryService.getCatById(id));
		return "categoriesAdd";
	}
	
	
	// Product Controller
	
	@GetMapping("/admin/products")
	public String getProducts(Model theModel) {
		theModel.addAttribute("products", productService.getAllProducts() );
		return "products";
	
}
	@GetMapping("/admin/products/add")
	public String addProducts(Model theModel) {
		theModel.addAttribute("productDTO", new ProductDTO());
		theModel.addAttribute("categories", categoryService.getAllCat());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String addProductPost(@ModelAttribute("productDTO")ProductDTO theProductDTO, 
			@RequestParam("productImage")MultipartFile file, 
			@RequestParam("imgName")String imgName) throws IOException {
		
		Product product = new Product();
		product.setName(theProductDTO.getName());
		product.setId(theProductDTO.getId());
		product.setCategory(categoryService.getCatById(theProductDTO.getCategoryId()));
		product.setPrice(theProductDTO.getPrice());
		product.setWeight(theProductDTO.getWeight());
		product.setDescription(theProductDTO.getDescription());
		
		String imageUUID;
	if(!file.isEmpty()) {
		imageUUID=file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
		Files.write(fileNameAndPath, file.getBytes());
	}
	else {
		imageUUID = imgName;
	}
	
	product.setImageName(imageUUID);
	productService.addProduct(product);
	
	System.out.println(System.getProperty("user.dir")+"/src/main/resources/static/productImages");
	
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProducts(@PathVariable("id") long id) {
		productService.removeProductById(id);
		
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable("id") long id, Model theModel) {
		Product theProduct =  productService.getProductById(id).get();
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setName(theProduct.getName());
		productDTO.setPrice(theProduct.getPrice());
		productDTO.setDescription(theProduct.getDescription());
		productDTO.setWeight(theProduct.getWeight());
		productDTO.setId(theProduct.getId());
		productDTO.setCategoryId(theProduct.getCategory().getId());
		
		productDTO.setImageName(theProduct.getImageName());
		
		theModel.addAttribute("categories", categoryService.getAllCat());
		theModel.addAttribute("productDTO", productDTO);
		
		return "productsAdd";
	}
}
