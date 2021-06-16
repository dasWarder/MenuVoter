package com.example.service.vote;

import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.exception.EntityNotFoundException;

public interface VoteService {
    MenuRatedDto voteForMenu(VoteDto voteDto, Long restaurantId) throws EntityNotFoundException;
}
