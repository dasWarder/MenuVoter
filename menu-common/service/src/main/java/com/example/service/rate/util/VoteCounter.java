package com.example.service.rate.util;


import com.example.menu.Menu;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;



@Slf4j
@Component
@NoArgsConstructor
public class VoteCounter {

    private ConcurrentHashMap<String, AtomicLong> votes = new ConcurrentHashMap<>();

    public Long incrementVoteCounterForMenu(Menu menu) {

        votes.computeIfAbsent(menu.getId(), key -> {

                log.info("Create a new counter for the menu with ID = {}",
                                                                          menu.getId());
                return new AtomicLong(menu.getVotesCount());
        });

        log.info("Add one vote to votes for menu with ID = {}, current count = {}",
                                                                                    menu.getId(),
                                                                                     votes.get(menu));
        AtomicLong votesCount = votes.get(menu.getId());
        long vote = votesCount.incrementAndGet();

        return vote;
    }

    public Long getCurrentCountOfVotesForMenu(Menu menu) {

        votes.computeIfAbsent(menu.getId(), k -> {

                log.info("Create a new counter for the menu with ID = {}",
                                                                         menu.getId());
                return new AtomicLong(menu.getVotesCount());
        });

        log.info("Receiving a counter without incrementation for menu with ID = {}",
                                                                                   menu.getId());
        long counter = votes.get(menu.getId()).get();

        return counter;
    }
}
