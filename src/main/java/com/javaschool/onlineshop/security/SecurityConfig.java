package com.javaschool.onlineshop.security;

import com.javaschool.onlineshop.security.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.javaschool.onlineshop")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   private final UserDetailsServiceImpl userDetailsService;

   private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
        .authorizeRequests().antMatchers( "/cart", "/product/active").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/statistics").hasAnyAuthority("ADMIN")
                .antMatchers("/order").permitAll()
                .antMatchers("/order/info").hasAnyAuthority("CUSTOMER", "ADMIN")
                .antMatchers("/order/status").hasAnyAuthority("ADMIN")
                .antMatchers("/product/all", "/orders/all").hasAnyAuthority("ADMIN")
                .antMatchers("/product/deletion", "/product/edition").hasAnyAuthority("ADMIN")
                .antMatchers("/product/{productId}", "/product/categories").permitAll()
                .antMatchers("/product/new", "product/category").hasAnyAuthority("ADMIN")
                .antMatchers("/media/**").permitAll()
                .antMatchers("/authentication/login").permitAll().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
