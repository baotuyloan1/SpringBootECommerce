package com.bnd.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private final EmployeeDetailsServiceImpl employeeDetailsService;

  @Autowired
  public WebSecurityConfig(EmployeeDetailsServiceImpl employeeDetailsService) {
    this.employeeDetailsService = employeeDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    //    Map<String, PasswordEncoder> encoders = new HashMap<>();
    //    encoders.put("bcrypt", new BCryptPasswordEncoder());
    //    return new DelegatingPasswordEncoder("bcrypt", encoders);
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(employeeDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
  @DependsOn
  public void myConfigure( AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  //  using in-memory users
  //  @Bean
  //  public UserDetailsService userDetailsService(
  //      PasswordEncoder passwordEncoder, AuthenticationManagerBuilder auth) throws Exception {
  ////    UserDetails user =
  ////
  // User.withUsername("bao").password(passwordEncoder.encode("password")).roles("USER").build();
  ////    UserDetails admin =
  ////        User.withUsername("admin")
  ////            .password(passwordEncoder.encode("adminpwd"))
  ////            .roles("USER", "ADMIN")
  ////            .build();
  //
  //    auth.userDetailsService(employeeDetailsService).passwordEncoder(passwordEncoder);
  //
  //    return new InMemoryUserDetailsManager(user, admin);
  //  }

  @Bean
  public SecurityFilterChain myFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/rawUI/")
        .permitAll()
        .antMatchers("/rawUI/**")
        .hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/rawUI/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .permitAll()
        //        .failureUrl("/rawUI/login?message=wrongpassword")
        .and()
        .logout()
        .logoutUrl("/rawUI/logout")
        .logoutSuccessUrl("/rawUI/?logoutStatus=true")
        .permitAll();
    //        .and()
    //        .httpBasic();
    return http.build();
  }
}
