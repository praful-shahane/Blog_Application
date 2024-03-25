package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

	private Long id;
	
	/*
	 * client requirement is 
	 * title should not be null or empty
	 * title should have at least 2 characters
	 */
	
	
	@NotNull
	@Size(min = 2,message = "post title should have at least 2 characters")
	private String title;
	
	/*
	 * client requirement is 
	 * description should not be null or empty
	 * description should have at least 10 characters
	 */
	@NotNull
	@Size(min = 10,message = "post description should have at least 10 characters")
	private String description;
	
	/*
	 * client requirement is 
	 * content should not be null or empty
	 * 
	 */
	@NotNull
	private String content;
	
	private Set<CommentDto> comments;
	
	
	private Long categoryId;
	
	
}
