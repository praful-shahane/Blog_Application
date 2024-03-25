package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;



/*
 * this exception is throw whenever we write some business logic or validate request parameters
 */
public class BlogApiException  extends RuntimeException{

	private HttpStatus status;
	private String message;
	
	
	public BlogApiException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}


	public HttpStatus getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}
	
	
	
	
	
}
