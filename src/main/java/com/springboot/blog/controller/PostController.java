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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	
	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	
	//create blog post 
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost( @Valid @RequestBody PostDto  postDto){
		
		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	//get all posts rest api
	@GetMapping
	public PostResponse getAllPost(
			
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDire", defaultValue =AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDire
			){
		
		return postService.getAllPost(pageNo,pageSize,sortBy,sortDire); 
	}
	
	
	//get Post by Id;
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
		
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	//update post id by rest api.
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost( @Valid @RequestBody PostDto postDto,@PathVariable(name = "id") long id ){
		
		PostDto postDtoResponse = postService.updatePost(postDto, id);
		
		return new ResponseEntity<PostDto>(postDtoResponse, HttpStatus.OK);
	}
	
	//delete post rest api
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
		
		 postService.deletePostById(id);
		return new ResponseEntity<String>("Post Entity deleted Successfully ",HttpStatus.OK);
	}
	
	//Build post by category restapi
	//Url==> http://localhost:8080/api/posts/category/8
	@GetMapping("/category/{id}")
	public  ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id")  Long categoryId){
		
		List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(postDtos);
	}
	

}
