package com.reddit.springredditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reddit.springredditclone.dto.VoteDto;
import com.reddit.springredditclone.exceptions.PostNotFoundException;
import com.reddit.springredditclone.exceptions.SpringRedditException;
import com.reddit.springredditclone.model.Post;
import com.reddit.springredditclone.model.Vote;
import com.reddit.springredditclone.repository.PostRepository;
import com.reddit.springredditclone.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.reddit.springredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	public void vote(VoteDto voteDto) {
		
		Post post = postRepository.findById(voteDto.getPostId())
					 .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
		
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
		
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
        	
        	if(post.getVoteCount() == null ) {
        		post.setVoteCount(0);
        		post.setVoteCount(post.getVoteCount() - 1);
        		
        	} else {
        		post.setVoteCount(post.getVoteCount() - 1);
        	}
        }
        
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
	}
	
	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
}
