package com.reddit.springredditclone.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.springredditclone.dto.PostRequest;
import com.reddit.springredditclone.dto.PostResponse;
import com.reddit.springredditclone.exceptions.PostNotFoundException;
import com.reddit.springredditclone.exceptions.SubredditNotFoundException;
import com.reddit.springredditclone.mapper.PostMapper;
import com.reddit.springredditclone.model.Post;
import com.reddit.springredditclone.model.Subreddit;
import com.reddit.springredditclone.model.User;
import com.reddit.springredditclone.repository.PostRepository;
import com.reddit.springredditclone.repository.SubredditRepository;
import com.reddit.springredditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	
	@Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }
	
	@Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
	
	public void save(PostRequest postRequest) {
		
		// System.out.println("Hello from save method....");
		
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        
        // System.out.println(subreddit.getName());
        
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }
	
	@Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }
	
	@Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
