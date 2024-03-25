package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	//Built Add Category Rest API
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedcategoryDto = categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(savedcategoryDto, HttpStatus.CREATED);
	}

	
	//Buil get category rest api by id
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory( @PathVariable("id") Long categoryId){
		
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
		
	}
	
	//Build getAll Categories RestAPI
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategory(){
		
		List<CategoryDto> CategoriesDto = categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDto>>(CategoriesDto, HttpStatus.OK);
		
	}
	
	
	//Build Update category RestApI
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, 
											@PathVariable("id") Long categoryId){
		
		CategoryDto updateCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategoryDto, HttpStatus.OK);
	}
	
	//Built delete category REST API
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory( @PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<String>("Category deleted successfully...", HttpStatus.OK);
	}
	
	
	
}
