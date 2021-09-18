package com.example.defaultSecurity.config;

import com.example.defaultSecurity.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;
    @Autowired
    private UserRepository userRepository;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable().authorizeRequests().antMatchers("/api/**").authenticated().and()
        //         // .httpBasic();
        //         .formLogin();


         // remove csrf and state in session because in jwt we do not need them
         http.csrf().disable()
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         // add jwt filters (1. authentication, 2. authorization)
         .addFilter(new JwtAuthenticationFilter(authenticationManager()))
         .addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
         .authorizeRequests().antMatchers("/api/**").authenticated();
         // configure access rules

        http.headers().frameOptions().sameOrigin();

    }


    @Bean
   public PasswordEncoder  encoder() {
       return new BCryptPasswordEncoder();
   }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        
        // // PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // auth.inMemoryAuthentication()
        // .withUser("admin")
        // // .password(encoder.encode("pass"))
        // .password(encoder().encode("pass"))
        // .roles("USER");

        auth.authenticationProvider(authenticationProvider());

    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

}
