package com.reddit.springredditclone.dto;

import com.reddit.springredditclone.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

	private VoteType voteType;
    private Long postId;
}
