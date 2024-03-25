package com.springboot.blog.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	private String name;
	private String email;
	private String body;
	
	
	/*@ManyToOne ==>
	 * multiple comments has  one post
	 */
	/*
	 * FetchType.LAZY tells hibernate to only fetch the related entities 
	 * from the database when you use the relationship.
	 * 
	 * @JoinColumn  => to specify foreign key
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id" ,nullable = false)
	private Post post;
	
	
	
}
