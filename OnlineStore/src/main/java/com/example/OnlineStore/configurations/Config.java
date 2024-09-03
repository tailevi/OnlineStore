package com.example.OnlineStore.configurations;

import com.example.OnlineStore.reposetories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class Config {
    @Autowired
    private final UserRepo userRepo;
    @Bean
    public RestTemplate URLRequest(){
        return new RestTemplate();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return username ->
                userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
