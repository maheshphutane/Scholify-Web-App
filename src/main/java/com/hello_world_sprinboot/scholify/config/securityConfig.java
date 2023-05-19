package com.hello_world_sprinboot.scholify.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder;

@Configuration
public class securityConfig {
   /**
    * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
    * to move towards a component-based security configuration. It is recommended to create a bean
    * of type SecurityFilterChain for security related configurations.
    * @param http
    * @return SecurityFilterChain
    * @throws Exception
    */
//   @Bean
//   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//      // Permit All Requests inside the Web Application
//      http.authorizeHttpRequests().anyRequest().permitAll().
//              and().formLogin()
//              .and().httpBasic();
//
//      // Deny All Requests inside the Web Application
//            /*http.authorizeHttpRequests().anyRequest().denyAll().
//                    and().formLogin()
//                    .and().httpBasic();*/
//
//      return http.build();
//
//   }
   /**
    * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
    * to move towards a component-based security configuration. It is recommended to create a bean
    * of type SecurityFilterChain for security related configurations.
    * @param http
    * @return SecurityFilterChain
    * @throws Exception
    */
   @Bean
   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
      http.csrf().ignoringRequestMatchers("/saveMsg")
              .ignoringRequestMatchers("/scholify/actuator/**").ignoringRequestMatchers("/api/**").ignoringRequestMatchers("/hall-explorer/**").and()
              .authorizeHttpRequests()
              .requestMatchers("/dashboard").authenticated()
              .requestMatchers("/displayProfile").authenticated()
              .requestMatchers("/updateProfile").authenticated()
              .requestMatchers("/scholify/actuator/**").hasRole("ADMIN")
              .requestMatchers("/hall-explorer/**").authenticated()
              .requestMatchers("/student/**").hasRole("STUDENT")
              .requestMatchers("/displayMessages/**").hasRole("ADMIN")
              .requestMatchers("/closeMsg/**").hasRole("ADMIN")
              .requestMatchers("/admin/**").hasRole("ADMIN")
              .requestMatchers("/index").permitAll()
              .requestMatchers("/holidays/**").permitAll()
              .requestMatchers("/contact").permitAll()
              .requestMatchers("/saveMsg").permitAll()
              .requestMatchers("/courses").permitAll()
              .requestMatchers("/about").permitAll()
              .requestMatchers("/login").permitAll()
              .requestMatchers("/api/**").authenticated()
              .requestMatchers("/logout").permitAll()
              .requestMatchers("/public/**").permitAll()
              .requestMatchers("/assets/**").permitAll()
              .and().formLogin().loginPage("/login")
              .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
              .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
              .and().httpBasic();

      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

}

