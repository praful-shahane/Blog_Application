package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogApiException;

import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private Long jwtExpirationDate;
	
	
	//create method to generate JWT Token
	
	public String generateToken(Authentication authentication) {
		
		String username=authentication.getName();
		Date currentDate= new Date();
		
		Date expireDate= new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token=Jwts.builder()
						.setSubject(username)
						.setIssuedAt(currentDate)
						.setExpiration(expireDate)
						.signWith(key())
						.compact();
		
		return token;
	}
	
	
	//to get key and need to provide into signWith () method
	private Key key() {
		
		//getting jwtsecret from application.properties and decode into base64 format
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		return key;
	}
	
	
	//get Username from JWT token
	public String getUsername(String token) {
		
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	
	//validate JWT Token
	public boolean validateToken(String token) {
		
		try {
			
			Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
			return true;
			
		} catch (MalformedJwtException malformedJwtException) {
			
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token....");
			
			
		}catch( ExpiredJwtException expiredJwtException ) {
			
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired  JWT Token....");
			
		}catch (UnsupportedJwtException unsupportedJwtException) {
			

			throw new BlogApiException(HttpStatus.BAD_REQUEST, "UNsupported   JWT Token....");
		}
		catch (IllegalArgumentException illegalArgumentException) {

			throw new BlogApiException(HttpStatus.BAD_REQUEST, " JWT claims   String is empty....");
		}
		
		
	}
	
	

}
