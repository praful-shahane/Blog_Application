package com.springboot.blog.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ErrorDetails {

	private Date timestamp;
	private String message;
	private String details;
	
	
	
}
