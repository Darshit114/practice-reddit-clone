package com.reddit.springredditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.springredditclone.dto.CommentsDto;
import com.reddit.springredditclone.service.CommentService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {

	private final CommentService commentService;
	
	@PostMapping("create-comment")
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.createComment(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("by-post")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam("postId") Long postId) {
        return status(OK)
                .body(commentService.getCommentsByPost(postId));
    }

    @GetMapping("by-user")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@RequestParam("userName") String userName) {
        return status(OK).body(commentService.getCommentsByUser(userName));
    }
}
