package com.reddit.springredditclone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.reddit.springredditclone.dto.SubredditDto;
import com.reddit.springredditclone.model.Post;
import com.reddit.springredditclone.model.Subreddit;


@Mapper(componentModel = "spring")
public interface SubredditMapper {

	@Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
	
	@InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}
