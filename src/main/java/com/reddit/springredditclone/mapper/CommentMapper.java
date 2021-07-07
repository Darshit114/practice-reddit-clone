package com.reddit.springredditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.springredditclone.dto.CommentsDto;
import com.reddit.springredditclone.model.Comment;
import com.reddit.springredditclone.model.Post;
import com.reddit.springredditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	Comment map(CommentsDto commentDto, Post post, User user);
	
	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);
}
