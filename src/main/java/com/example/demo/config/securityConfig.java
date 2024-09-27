package com.example.demo.config;

import com.example.demo.service.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class securityConfig {
    private CustomUserDetailService customUserDetailService;
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public securityConfig(CustomUserDetailService customUserDetailService,
                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailService = customUserDetailService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }


    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests((auth) -> auth.
                        requestMatchers("/**").permitAll().
//                        requestMatchers("/admin/**").permitAll().
                        requestMatchers("/admin/**").hasAuthority("ADMIN").
                        anyRequest().authenticated()
                ).formLogin(
                        login->login.loginPage("/loginPage").
                                loginProcessingUrl("/login").
                                usernameParameter("email").
                                passwordParameter("password").
                                successHandler(customAuthenticationSuccessHandler)
                ).logout(
                        logout->logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/home")
                ).exceptionHandling(
                        configurer->configurer.accessDeniedPage("/showPage403")
                );
        return http.build();
    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/static/**", "/fe/**", "/assets/**");
    }
}
