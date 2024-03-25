package com.springboot.blog.payload;

import java.util.List;

import com.springboot.blog.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	/*
	 * PostResponse class is made to send extra details of  pagination  with content
	 */

	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
	
}
