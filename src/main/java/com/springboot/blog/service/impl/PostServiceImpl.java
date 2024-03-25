package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	
	private ModelMapper mapper;

	private CategoryRepository categoryRepository;
	
	

	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
		super();
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository=categoryRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
		.orElseThrow(()-> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));

		// convert DTO to Entity
		Post post = mapToEntity(postDto);
		post.setCategory(category);

		// saved Post Entity into DB
		Post newPost = postRepository.save(post);

		// convert entity to DTO
		PostDto postResponse = mapToDTO(newPost);

		return postResponse;
	}

	@Override
	public PostResponse getAllPost( int pageNo,int pageSize,String sortBy,String sortDire) {
		
		//create Pageable instance 
		//if we need only pagination we use below code.
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		
		/*
		 * //if we need pagination and sorting we use below code. //bydeafult sorting is
		 * ascending order. Pageable pageable = PageRequest.of(pageNo,
		 * pageSize,Sort.by(sortBy));
		 */
		 
		//if we need  pagination and sorting   we use below code. 
			//bydeafult sorting is descending  order.
//			 Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).descending());
			 
		
			 /*
			  * if we want to sort data dyanmically in asceding/desceding order
			  *  we need to pass paramter in URL
			  */
			 
		Sort sort=sortDire.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
				Sort.by(sortBy).ascending():Sort.by(sortBy).descending() ;
		
			 Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

		Page<Post> posts = postRepository.findAll(pageable);
		
		//get content for page object
		List<Post> listPost = posts.getContent();
		
		List<PostDto> content = listPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

		     PostResponse postResponse = new PostResponse();
		     postResponse.setContent(content);
		     postResponse.setPageNo(posts.getNumber());
		     postResponse.setPageSize(posts.getSize());
		     postResponse.setTotalElements(posts.getTotalElements());
		     postResponse.setTotalPages(posts.getTotalPages());
		     postResponse.setLast(posts.isLast());
		     
		
		return postResponse;
	}

	// convert entity to DTO
	private PostDto mapToDTO(Post post) {
		
		//using ModelMAPPER library we converting Entity to DTO obejct.
		PostDto postDto = mapper.map(post, PostDto.class);
		
		/*
		 * PostDto postDto = new PostDto(); postDto.setId(post.getId());
		 * postDto.setTitle(post.getTitle());
		 * postDto.setDescription(post.getDescription());
		 * postDto.setContent(post.getContent());
		 */
		return postDto;
	}

	// convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {

		//using ModelMAPPER library we converting DTO  to Entity obejct.
		Post post = mapper.map(postDto, Post.class);
		
		/*
		 * Post post = new Post(); post.setTitle(postDto.getTitle());
		 * post.setDescription(postDto.getDescription());
		 * post.setContent(postDto.getContent());
		 */
		return post;
	}

	@Override
	public PostDto getPostById(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		System.out.println(post.toString());
		PostDto postDto = mapToDTO(post);
		return postDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		
		// get post by id from Database if present we will update details else throw exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
		.orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		PostDto updatedPostDto = mapToDTO(updatedPost);
		return updatedPostDto;
	}

	@Override
	public void deletePostById(Long id) {
		
		// get post by id from Database if present we will update details else throw exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
				
		postRepository.delete(post);
	}

	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		
		//first we check category id present in DB or not
		Category category = categoryRepository.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts= postRepository.findByCategoryId(categoryId);
		
		
		return posts.stream().map((post)->mapToDTO(post)).collect(Collectors.toList());
	}

}
