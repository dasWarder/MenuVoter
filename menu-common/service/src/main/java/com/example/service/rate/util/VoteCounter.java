package com.example.service.rate.util;


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

    public Long incrementCounter(String menuId) {
        votes.computeIfAbsent(menuId, k -> {

            log.info("Create a new counter for the menu with ID = {}", menuId);
            return new AtomicLong(0);

        });

        log.info("Add one vote to votes for menu with ID = {}, current count = {}", menuId, votes.get(menuId));
        long vote = votes.get(menuId).incrementAndGet();

        return vote;
    }

    public Long getCurrentCount(String menuId) {
        votes.computeIfAbsent(menuId, k -> {

            log.info("Create a new counter for the menu with ID = {}", menuId);
            return new AtomicLong(0);

        });

        log.info("Receiving a counter without incrementation for menu with ID = {}", menuId);
        long counter = votes.get(menuId).get();

        return counter;
    }
}
