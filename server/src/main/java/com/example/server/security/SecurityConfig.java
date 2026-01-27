package com.example.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.server.security.jwt.AuthEntryPointJwt;
import com.example.server.security.jwt.AuthTokenFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    AuthEntryPointJwt authEntryPointJwt;
    
    @Bean
    AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    };

    @Autowired
    UserDetailsService userDetailsService;
    
    @Bean 
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig){
        return authConfig.getAuthenticationManager();
    }

   @Bean
   public SecurityFilterChain filterChainMethod(HttpSecurity http)
   {
    http.csrf(csrf-> csrf.disable())
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(request->
        request
        .requestMatchers("/api/v2/test").permitAll()
        .anyRequest().authenticated()

    ).exceptionHandling(execption-> execption.authenticationEntryPoint(authEntryPointJwt));
    http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    http.authenticationProvider(authenticationProvider());
    return http.build();
    
   } 
    
}
