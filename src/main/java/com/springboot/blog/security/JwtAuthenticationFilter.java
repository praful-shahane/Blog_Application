package com.springboot.blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	
	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;
	
	
	

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}




	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get JWT Token from HTTPRequest
		String token = getTokenFromRequest(request);
		
		//validate token
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			//GET USERNAME from Token
			String username = jwtTokenProvider.getUsername(token);
			
			//loads user assciated with token
			UserDetails userDetails =userDetailsService.loadUserByUsername(username);
			
			//create instance of username and password
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//set authenticationtoken
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
			
		}
		
		filterChain.doFilter(request, response);
	
		
	}
	
	
	private String getTokenFromRequest(HttpServletRequest request) {
		
		//get bearerToken from request object.
		String bearerToken=request.getHeader("Authorization");
		//we get bearer+token.
		
		//extract only token from bearerToken.
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		
		return null;
	}
	
	
	
	
	
	
	

}
