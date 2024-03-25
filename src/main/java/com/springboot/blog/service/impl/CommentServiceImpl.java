package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	
	private CommentRepository commentRepository;
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;


	


	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}


	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {
		
		//convert commentDto to CommentEntity
		Comment comment = mapToEntity(commentDto);
		
		//get post record from DB
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
		
		//set post to comment 
		comment.setPost(post);
		
		//save comment entity into DB
		Comment newComment = commentRepository.save(comment);
		
		//convert CommentEntity to Comment DTO
		CommentDto savedCommentDto = mapToDTO(newComment);
		
		return savedCommentDto;
	}

	
	private CommentDto mapToDTO(Comment comment) {
		
		//using ModelMAPPER library we converting Entity to DTO obejct.
		 CommentDto commentDto =mapper.map(comment,CommentDto.class);
		
		/*
		 * CommentDto commentDto = new CommentDto(); commentDto.setId(comment.getId());
		 * commentDto.setBody(comment.getBody());
		 * commentDto.setEmail(comment.getEmail());
		 * commentDto.setName(comment.getName());
		 */
		return commentDto;
		
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		
		//using ModelMAPPER library we converting DTO to Entity obejct.
		Comment comment =mapper.map(commentDto, Comment.class);
		
		
		/*
		 * Comment comment = new Comment(); comment.setId(commentDto.getId());
		 * comment.setBody(commentDto.getBody());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setName(commentDto.getName());
		 */
		return comment;
		
	}


	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		
		//retrieve comments by PostId
		List<Comment> comments=commentRepository.findByPostId(postId);
		
		//convert list of comment entity into list of comment dto
		
		List<CommentDto> listCommentDto = comments.stream().map(comment-> mapToDTO(comment)).collect(Collectors.toList());
		
		return listCommentDto;
	}


	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		
		//get post record from DB
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
				
		//retrive comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));;
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
		}
		
		//maptoDto
		CommentDto commentDto = mapToDTO(comment);
		
		
		return commentDto;
	}


	@Override
	public CommentDto updateCommentById(Long postId, Long commentId,CommentDto commentRequest) {
		
		//get post record from DB
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
						
		//retrive comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));;
						
			
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		CommentDto updatedCommentDto = mapToDTO(updatedComment);
		
		return updatedCommentDto;
	}


	@Override
	public void deleteCommentById(Long postId, Long commentId) {
		
				//get post record from DB
				Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
								
				//retrive comment by id
				Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));;
								
					
				if(!comment.getPost().getId().equals(post.getId())) {
					throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comments does not belong to post");
				}
				
			commentRepository.delete(comment);	
		
	}
	
}
