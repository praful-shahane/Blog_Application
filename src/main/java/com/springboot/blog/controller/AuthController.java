package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	
	
	//	Build Login/Sign in rest api.
	@PostMapping(value={"/login","/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody   LoginDto loginDto){
		 String token = authService.login(loginDto);
		 
		 JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		 jwtAuthResponse.setAccessToken(token);
		 
		 return ResponseEntity.ok(jwtAuthResponse);
		
	}
	
	//Build Register/sign-up restAPI
	@PostMapping(value  ={"register","signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		
		String response = authService.register(registerDto);
		return  new ResponseEntity<String>(response,HttpStatus.CREATED);
		//as we are reqistering new user so we use creater 201 status.
		}
	
	
	
}
