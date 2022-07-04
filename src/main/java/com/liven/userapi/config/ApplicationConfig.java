package com.liven.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/**").permitAll();
    http.csrf().disable();
    
    return http.build();
  }
  
  @Bean
  PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
