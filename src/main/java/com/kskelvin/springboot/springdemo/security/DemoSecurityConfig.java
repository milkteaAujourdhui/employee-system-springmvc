package com.kskelvin.springboot.springdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // read data from database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        // handle bcrypt or noop (default)
        // return new JdbcUserDetailsManager(dataSource);

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // query for user and authority with custom table
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT user_id, pw, active from members where user_id=?"
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT user_id, role from roles where user_id=?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(res ->
                res.requestMatchers(HttpMethod.GET, "/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/employees/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/details/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/**").hasRole("ADMIN")
        );

        // use HTTP Basic Authentication
        http.httpBasic();

        // disable CSRF
        // in general not required for stateless REST APIS
        http.csrf().disable();

        return http.build();
    }

    // Get data from database
    /*@Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails user1 = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails user2 = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails user3 = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }*/
}
