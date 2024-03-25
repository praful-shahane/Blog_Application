package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	
	private CategoryRepository categoryRepository;
	
	private ModelMapper modelMapper;
	
	
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}




	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		
		//Convert DTO into entity using modelMapper which is 3rd party dependedncies.
	      Category category = modelMapper.map(categoryDto, Category.class);
      
      Category savedCategory = categoryRepository.save(category);
      return modelMapper.map(savedCategory, CategoryDto.class);
	}




	@Override
	public CategoryDto getCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("category", "id", categoryId));
		
		
		return modelMapper.map(category, CategoryDto.class);
	}




	@Override
	public List<CategoryDto> getAllCategories() {
		
		 List<Category> categories = categoryRepository.findAll();
		 List<CategoryDto> listCategoriesDto = categories.stream().map((category)->
			 modelMapper.map(category, CategoryDto.class)
		 ).collect(Collectors.toList());
		 
		return listCategoriesDto;
	}




	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setId(categoryId);
		
		Category updatedCategory = categoryRepository.save(category);
		
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}




	@Override
	public void deleteCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "id", categoryId));
	
		categoryRepository.delete(category);
		
		
		
		
		
	}

}
