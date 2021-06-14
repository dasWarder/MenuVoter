package com.example.service.vote;

import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;

public interface VoteService {
    MenuRatedDto vote(VoteDto voteDto, Long restaurantId);
}
