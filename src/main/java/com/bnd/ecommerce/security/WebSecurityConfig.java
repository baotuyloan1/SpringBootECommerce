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
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

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
    return new BCryptPasswordEncoder();
  }

  @Bean
  @DependsOn
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(employeeDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @DependsOn
  public void myConfigure(AuthenticationManagerBuilder auth){
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public SecurityFilterChain myFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/rawUI/newUser")
           // .hasAnyRole("ADMIN")  // only allow users with the "ROLE_ADMIN" authority
        .hasAnyAuthority("ADMIN","MANAGER") // only allow users with the "ADMIN" or "MANAGER" authority
        .antMatchers("/rawUI/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/rawUI/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
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
