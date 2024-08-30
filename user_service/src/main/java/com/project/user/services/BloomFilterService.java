package com.project.user.services;

import com.project.user.entities.User;
import com.project.user.repositories.UserRepository;
import lombok.Synchronized;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloomFilterService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserRepository userRepository;

    private RBloomFilter<String> emailBloomFilter;

    @EventListener(ApplicationReadyEvent.class)
    public void loadAllDataInBloomFilter() {
        RBloomFilter<String> stringRBloomFilter = getEmailBloomFilter();

        List<User> users = this.userRepository.findAll();

        users.forEach(user -> {
//            log.info("Name generated - " + user.getEmail());
            stringRBloomFilter.add(user.getEmail());
        });
    }


    @Synchronized
    public RBloomFilter<String> getEmailBloomFilter() {
        if (null == emailBloomFilter) {
            RBloomFilter<String> stringRBloomFilter = redissonClient.getBloomFilter("emails");

            //Set expectedInsertions and falseProbability
            stringRBloomFilter.tryInit(99999, 0.001);

            emailBloomFilter = stringRBloomFilter;
        }

        return emailBloomFilter;
    }
}
