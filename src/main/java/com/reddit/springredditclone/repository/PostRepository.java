package com.reddit.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.springredditclone.model.Post;
import com.reddit.springredditclone.model.Subreddit;
import com.reddit.springredditclone.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllBySubreddit(Subreddit subreddit);
	
	List<Post> findByUser(User user);
}
