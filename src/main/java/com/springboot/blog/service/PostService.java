package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPost( int pageNo,int pageSize,String sortBy,String sortDire);
	
	PostDto getPostById(Long id);
	
	PostDto updatePost(PostDto postDto,Long id);
	
	
	void deletePostById(Long id);
	
	
	List<PostDto> getPostsByCategory(Long categoryId);
}
