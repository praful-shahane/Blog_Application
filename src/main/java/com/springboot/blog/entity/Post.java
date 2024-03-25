package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "posts",
uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})} 
)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title" , nullable = false )
	private String title;
	
	@Column(name = "description" , nullable = false )
	private String description;
	
	@Column(name = "content" , nullable = false )
	private String content;
	
	/*
	 * cascade = CascadeType.ALL ==> we we remove parent data child data also gets remove.
	 * 
	 * orphanRemoval = true  ==>
	 */
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();
	
	/*
	 * FetchType.LAZY means whenever we load post object category object data will not load.
	 * we get catergory data by calling getcatergory 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catergory_id")
	private Category category;
	
}
