package com.example.springboot.first_rest_api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsRepository userDetailsRepository;

    // Constructor injection
    public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(Arrays.toString(args));
        userDetailsRepository.save(new UserDetailsEntity("Shree Krishna", "Bhagwan"));
        userDetailsRepository.save(new UserDetailsEntity("Shree Ram", "Avatar"));
        userDetailsRepository.save(new UserDetailsEntity("Parashuram", "Avatar"));
        userDetailsRepository.save(new UserDetailsEntity("Shiva", "Bhagwan"));

        List<UserDetailsEntity> allUsers = userDetailsRepository.findAll();
        List<UserDetailsEntity> bhagwans = userDetailsRepository.findByRole("Bhagwan");
        allUsers.forEach(userDetailsEntity -> logger.info(String.valueOf(userDetailsEntity)));
        bhagwans.forEach(userDetailsEntity ->
                logger.info("Bhagwan: {}", String.valueOf(userDetailsEntity)));
    }
}
